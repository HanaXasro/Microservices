package com.erp.apigateway.middlewares;


import com.erp.apigateway.dto.SecretDto;
import com.erp.apigateway.producer.RabbitProducer;
import com.erp.apigateway.services.IUserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.*;

@Component
public class JwtMiddleware implements GlobalFilter, Ordered {

    private final IUserContext userContext;
    private final RabbitProducer rabbitProducer;

    public JwtMiddleware(IUserContext userContext, RabbitProducer rabbitProducer) {
        this.userContext = userContext;
        this.rabbitProducer = rabbitProducer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = getAuthHeader(request);

        // Skip JWT validation for public endpoints
        if (shouldSkipAuth(request)) {
            return chain.filter(exchange);
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String secret = rabbitProducer.sendAndReceive("user.secret");

                byte[] keyBytes = Base64.getDecoder().decode(secret);

                // Check token expiration
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                Date expiration = claims.getExpiration();
                if (expiration != null && expiration.before(new Date())) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                Long userId = claims.get("userId", Long.class);
                String username = claims.getSubject();

                List<String> permissions = claims.get("permissions", List.class);
                // Save user context
                userContext.setUserId(userId);
                userContext.setUsername(username);
                userContext.setPermissions(permissions);

                // Add headers for downstream services
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Id", userId.toString())
                        .header("X-User-Name", username)
                        .header("X-User-Permissions", String.join(",", permissions))
                        .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private boolean shouldSkipAuth(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        return path.startsWith("/api/auth/") ||
                path.contains("/public/") ||
                path.contains("/swagger-ui/") ||
                path.contains("/v3/api-docs") ||
                path.contains("/actuator/");
    }

    private String getAuthHeader(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().getOrEmpty("Authorization");
        return headers.isEmpty() ? null : headers.getFirst();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
package com.erp.userservice.middlewares;

import com.erp.userservice.helper.JwtTokenUtil;
import com.erp.userservice.services.IUserContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtMiddleware extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private IUserContext userContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);

                Long userId = claims.get("userId", Long.class);
                String username = claims.getSubject();
                List<String> permissions = claims.get("permissions", List.class);

                // Save user details into request-scoped bean
                userContext.setUserId(userId);
                userContext.setUsername(username);
                userContext.setPermissions(permissions);

                // Forward headers for microservices
                request.setAttribute("X-User-Id", userId);
                request.setAttribute("X-User-Name", username);
                request.setAttribute("X-User-Permissions", String.join(",", permissions));

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired JWT token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

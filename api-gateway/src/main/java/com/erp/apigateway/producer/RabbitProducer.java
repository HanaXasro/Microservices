package com.erp.apigateway.producer;

import com.erp.apigateway.dto.SecretDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("user.exchange")
    private String userExchangeName;

    public String sendAndReceive(String routingKey , Object message ) {
        return rabbitTemplate.convertSendAndReceiveAsType(userExchangeName, routingKey ,message ,
                new ParameterizedTypeReference<String>() {});
    }

    public <T, E> E sendAndReceive(String routingKey, T request, Class<E> responseType) {
        return rabbitTemplate.convertSendAndReceiveAsType(
                userExchangeName,
                routingKey,
                request,
                new ParameterizedTypeReference<E>() {
                }
        );
    }


    public String sendAndReceive(String routingKey) {
        return sendAndReceive(routingKey, "request-secret");
    }

}

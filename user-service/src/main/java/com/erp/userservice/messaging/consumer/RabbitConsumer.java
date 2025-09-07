package com.erp.userservice.messaging.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RabbitConsumer {

    @Value("${jwt.secret}")
    private String secret;

    @RabbitListener(queues = "user.secret.queue")
    public String consumeSecretKey() {
        return secret;
    }

}

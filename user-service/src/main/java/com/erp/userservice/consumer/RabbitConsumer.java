package com.erp.userservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RabbitConsumer {

    @Value("${jwt.secret}")
    private String secret;


    @RabbitListener(queues = "user.info.queue")
    public void consumeUserInfo(Long userId) {
        System.out.println("User Info request: " + userId);
    }

    @RabbitListener(queues = "user.permissions.queue")
    public void consumeUserPermissions(Long userId) {
        System.out.println("User Permissions request: " + userId);
    }

    @RabbitListener(queues = "user.claims.queue")
    public String consumeSecretKey() {
        return secret;
    }

}

package com.erp.apigateway.messaging.consumer;

import com.erp.apigateway.services.IUserContext;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitConsumer {

    private final IUserContext userContext;

    public RabbitConsumer(IUserContext userContext) {
        this.userContext = userContext;
    }

    @RabbitListener(queues = "permission.user.queue")
    public List<String> consumeSecretKey() {
        return userContext.getPermissions();
    }
}

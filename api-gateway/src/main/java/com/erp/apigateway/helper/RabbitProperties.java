package com.erp.apigateway.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitProperties {

    private String exchange;
    private List<QueueConfig> queues;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public List<QueueConfig> getQueues() {
        return queues;
    }

    public void setQueues(List<QueueConfig> queues) {
        this.queues = queues;
    }

    public static class QueueConfig {
        private String name;
        private String routingKey;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }
    }

}

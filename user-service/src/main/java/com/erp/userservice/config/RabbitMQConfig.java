package com.erp.userservice.config;

import com.erp.userservice.helper.RabbitProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
@Configuration
public class RabbitMQConfig {

    private final RabbitProperties rabbitProperties;

    public RabbitMQConfig(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

    // Keep exchange durable (true) or make auto-delete depending on your requirement
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitProperties.getExchange());
        // durable=true, autoDelete=false
    }

    // Declare queues as non-durable and auto-delete
    @Bean
    public List<Queue> declareQueues() {
        return rabbitProperties.getQueues().stream()
                .map(config -> new Queue(config.getName(), true))
                // durable=false, exclusive=false, autoDelete=true
                .toList();
    }

    // Bind queues to the exchange
    @Bean
    public List<Binding> declareBindings(DirectExchange exchange) {
        List<Binding> bindings = new ArrayList<>();
        for (RabbitProperties.QueueConfig config : rabbitProperties.getQueues()) {
            Queue queue = new Queue(config.getName(), true);
            bindings.add(BindingBuilder.bind(queue).to(exchange).with(config.getRoutingKey()));
        }
        return bindings;
    }

    // Declarables to create exchange and queues automatically
    @Bean
    public Declarables rabbitDeclarables() {
        List<Declarable> declarable = new ArrayList<>();
        DirectExchange exchange = directExchange();

        declarable.add(exchange);

        for (RabbitProperties.QueueConfig config : rabbitProperties.getQueues()) {
            Queue queue = new Queue(config.getName(), true);
            declarable.add(queue);
            declarable.add(BindingBuilder.bind(queue).to(exchange).with(config.getRoutingKey()));
        }

        return new Declarables(declarable);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

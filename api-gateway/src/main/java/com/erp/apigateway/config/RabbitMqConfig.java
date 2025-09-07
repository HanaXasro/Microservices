package com.erp.apigateway.config;
import com.erp.apigateway.helper.RabbitProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RabbitMqConfig {

    private final RabbitProperties rabbitProperties;

    public RabbitMqConfig(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitProperties.getExchange());
    }

    @Bean
    public List<Queue> declareQueues() {
        return rabbitProperties.getQueues().stream()
                .map(config -> new Queue(config.getName(), true))
                // durable=false, exclusive=false, autoDelete=true
                .toList();
    }

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
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
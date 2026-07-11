package com.example.unitTestDemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE = "user.created.queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, false);
    }
}

package com.URLShortener.worker.configuration;

import com.URLShortener.worker.MyWorker;
import com.URLShortener.worker.services.RabbitMQPublisherService;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue validated() {
        return new Queue("validated");
    }

    @Bean
    public MyWorker receiver() {
        return new MyWorker(new RabbitMQPublisherService());
    }
}

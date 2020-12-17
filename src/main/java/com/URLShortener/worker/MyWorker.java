package com.URLShortener.worker;

import com.URLShortener.worker.domain.Work;
import com.URLShortener.worker.services.RabbitMQPublisherService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues = "validation_job")
public class MyWorker {
    @Autowired
    private final RabbitMQPublisherService rabbitMQPublisherService;

    public MyWorker(RabbitMQPublisherService rabbitMQPublisherService) {
        this.rabbitMQPublisherService = rabbitMQPublisherService;
    }

    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [x] Received '" + in + "'");

        Work w =  new Work(in);
        System.out.println(w.toString());
        try {
            this.rabbitMQPublisherService.send(w.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.URLShortener.worker;

import com.URLShortener.worker.services.RabbitMQPublisherService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RabbitListener(queues = "validation_resp")
class ValidationWorkerTests {


    @Autowired
    private RabbitTemplate template;

    @Autowired
    private final RabbitMQPublisherService rabbitMQPublisherService;


    static String resp;

    ValidationWorkerTests() {
        rabbitMQPublisherService = new RabbitMQPublisherService();
    }


    @BeforeEach
    void before(){
        resp = "";
    }

    @Test
    void getsAFalseValidationJob() throws InterruptedException {
        this.template.convertAndSend("validation_job", "asdcasdcas@https://falseSite.es@http://localhost:8080/r/9fb3dc42@false");
        Thread.sleep(10000);
        System.out.println("--" + resp);
        assert(resp.equals("asdcasdcas@http://localhost:8080/r/9fb3dc42@false@https://falseSite.es@false"));
    }

    @Test
    void getsATrueValidationJob() throws InterruptedException {
        this.template.convertAndSend("validation_job", "asdcasdcas@https://www.google.com@http://localhost:8080/r/9fb3dc42@false");
        Thread.sleep(10000);
        System.out.println(resp);
        assertEquals(resp, "asdcasdcas@http://localhost:8080/r/9fb3dc42@true@https://www.google.com@false");
    }
    @RabbitHandler
    public void receive(String in) {
        resp = in;
    }

}

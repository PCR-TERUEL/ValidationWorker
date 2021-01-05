package com.URLShortener.worker.domain;

import com.URLShortener.worker.services.RabbitMQPublisherService;
import com.URLShortener.worker.services.URLValidatorService;
import org.springframework.beans.factory.annotation.Autowired;

public class Work extends Thread{
    private String url;
    private String shortedUrl;
    private String sessionId;
    private String isCSV;
    private final String SEPARATOR = "@";
    private final RabbitMQPublisherService rabbitMQPublisherService;
    public Work(String input, RabbitMQPublisherService rabbitMQPublisherService) {
        String[] values = input.split(SEPARATOR);
        this.sessionId = values[0];
        this.url = values[1];
        this.shortedUrl = values[2];
        this.isCSV = values[3];
        this.rabbitMQPublisherService = rabbitMQPublisherService;
        start();
    }

    @Override
    public void run() {
        System.out.println(this.toString());
        try {
            this.rabbitMQPublisherService.send(this.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.sessionId + SEPARATOR + this.shortedUrl + SEPARATOR + isValid(this.url) + SEPARATOR + url +
                SEPARATOR + isCSV ;
    }

    private boolean isValid(String url) {
        URLValidatorService uvs = new URLValidatorService(url);
        return uvs.isValid();
    }
}

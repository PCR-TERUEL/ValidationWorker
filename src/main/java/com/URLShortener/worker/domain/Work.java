package com.URLShortener.worker.domain;

import com.URLShortener.worker.services.URLValidatorService;

public class Work {
    private String url;
    private String shortedUrl;
    private String sessionId;
    private String isCSV;
    private final String SEPARATOR = "@";
    public Work(String input) {
        String[] values = input.split(SEPARATOR);
        this.sessionId = values[0];
        this.url = values[1];
        this.shortedUrl = values[2];
        this.isCSV = values[3];
    }

    @Override
    public String toString() {
        return this.sessionId + SEPARATOR + this.shortedUrl + SEPARATOR + isValid(this.url) + SEPARATOR + url + SEPARATOR + isCSV ;
    }

    private boolean isValid(String url) {
        URLValidatorService uvs = new URLValidatorService(url);
        return uvs.isValid();
    }
}

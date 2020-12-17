package com.URLShortener.worker.domain;

import com.URLShortener.worker.services.URLValidatorService;

public class Work {
    private String url;
    private String jwt;
    private final String SEPARATOR = "#";
    public Work(String input) {
        String[] values = input.split(SEPARATOR);
        this.jwt = values[0];
        this.url = values[1];
    }

    @Override
    public String toString() {
        return this.jwt + SEPARATOR + this.url + SEPARATOR + isValid(this.url);
    }

    private boolean isValid(String url) {
        URLValidatorService uvs = new URLValidatorService(url);
        return uvs.isValid();
    }
}

package com.example.pr51salon.service;

public class CustomResourceNotFoundException extends RuntimeException {
    public CustomResourceNotFoundException(String message) {
        super(message);
    }
}
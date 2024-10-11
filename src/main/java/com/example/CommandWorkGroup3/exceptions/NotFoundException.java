package com.example.CommandWorkGroup3.exceptions;

import java.util.UUID;

public abstract class NotFoundException extends RuntimeException {
    private final String message;
    public NotFoundException(String message) {
        this.message = message;
    }

    public String getMes() {
        return message;
    }

}

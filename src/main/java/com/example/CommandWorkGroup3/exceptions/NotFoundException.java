package com.example.CommandWorkGroup3.exceptions;

import java.util.UUID;

public abstract class NotFoundException extends RuntimeException {
    private final long id;
    public NotFoundException(long id) {
        this.id = id;
    }

    public long getMes() {
        return id;
    }

}

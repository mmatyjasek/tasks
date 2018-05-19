package com.crud.tasks.exception;

public class NotFoundTaskException extends RuntimeException {
    public NotFoundTaskException(final String message) {
        super(message);
    }
}

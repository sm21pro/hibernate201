package com.srikanth.hibernate.exception;

public class EventRegistrationFailureException extends Exception {

    public EventRegistrationFailureException() {
        super();
    }

    public EventRegistrationFailureException(String message) {
        super(message);
    }

    public EventRegistrationFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventRegistrationFailureException(Throwable cause) {
        super(cause);
    }

    protected EventRegistrationFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

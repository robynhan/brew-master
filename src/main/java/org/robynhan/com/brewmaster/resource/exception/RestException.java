package org.robynhan.com.brewmaster.resource.exception;

public class RestException extends RuntimeException {
    public RestException(final String message) {
        super(message);
    }
}

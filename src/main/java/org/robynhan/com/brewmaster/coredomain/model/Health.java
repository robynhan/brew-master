package org.robynhan.com.brewmaster.coredomain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Health {
    @JsonProperty
    private String message;

    public Health() {

    }

    public Health(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

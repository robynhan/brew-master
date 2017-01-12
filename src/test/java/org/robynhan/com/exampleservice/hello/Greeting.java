package org.robynhan.com.exampleservice.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Greeting {
    @JsonProperty
    private final String id;
    @JsonProperty
    private final String content;

    public Greeting(){
        id = null;
        content = null;
    }

    public Greeting(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

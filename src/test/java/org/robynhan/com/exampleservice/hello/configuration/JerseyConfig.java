package org.robynhan.com.exampleservice.hello.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.robynhan.com.exampleservice.hello.resource.GreetingController;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerClasses(GreetingController.class);
    }
}

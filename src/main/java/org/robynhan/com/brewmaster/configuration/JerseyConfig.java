package org.robynhan.com.brewmaster.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.robynhan.com.brewmaster.resource.CaseFileResource;
import org.robynhan.com.brewmaster.resource.CaseFolderResource;
import org.robynhan.com.brewmaster.resource.HealthController;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerClasses(HealthController.class,
                CaseFileResource.class,
                CaseFolderResource.class);
    }
}

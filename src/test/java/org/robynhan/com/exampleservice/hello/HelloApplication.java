package org.robynhan.com.exampleservice.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

@EntityScan(basePackageClasses = {HelloApplication.class})
@SpringBootApplication(scanBasePackageClasses = {HelloApplication.class})
public class HelloApplication {
    private ConfigurableApplicationContext applicationContext;

    public void start() {
        System.getProperties().put("server.port", 4321);
        applicationContext = SpringApplication.run(HelloApplication.class);
    }

    public void stop() {
        applicationContext.close();
    }
}

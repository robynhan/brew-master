package org.robynhan.com.brewmaster.coredomain.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.robynhan.com.brewmaster.resource.exception.RestException;
import org.robynhan.com.brewmaster.util.Constants;
import org.robynhan.com.exampleservice.hello.HelloApplication;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServiceInvokerTest {
    private static HelloApplication helloApplication = new HelloApplication();
    private ServiceInvoker invoker = new ServiceInvoker();

    @BeforeClass
    public static void setup() {
        helloApplication.start();
    }

    @AfterClass
    public static void clean() throws InterruptedException {
        helloApplication.stop();
    }

    @Test
    public void should_fetch_resource_when_get() {
        String content = invoker.get("http://localhost:4321/greetings/1");
        assertThat(content, is("{\"id\":null,\"content\":null}"));
    }

    @Test(expected = RestException.class)
    public void should_throw_exception_when_use_bad_uri() {
        invoker.get("xxx");
    }

    @Test
    public void should_delete_resource_when_delete() {
        invoker.delete("http://localhost:4321/greetings/0");
        String content = invoker.get("http://localhost:4321/greetings/0");
        assertThat(content, is("{\"id\":null,\"content\":null}"));
    }

    @Test
    public void should_add_resource_when_post() {
        invoker.post("http://localhost:4321/greetings",
                Constants.PROJECT_FOLDER_PATH + "/src/test/resources/json/demo.json");
        String content = invoker.get("http://localhost:4321/greetings/100");
        assertThat(content, is("{\"id\":\"100\",\"content\":\"hello\"}"));
    }
}

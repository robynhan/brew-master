package org.robynhan.com.brewmaster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robynhan.com.brewmaster.coredomain.model.Health;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() {
        ResponseEntity<Health> entity = this.restTemplate.getForEntity("/health", Health.class);
        assertThat(entity.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(entity.getBody().getMessage(), is("Jersey: Up and Running!"));
    }
}
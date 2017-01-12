package org.robynhan.com.brewmaster.resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class BaseResourceTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void initRestTemplate() {
        restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    protected String getLocalUrl(final String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    public void should_create_resource_test() {
        assertThat(restTemplate, notNullValue());
    }


    protected <T> ResponseEntity<T> getForEntity(final String url, final Class<T> clazz) {
        return restTemplate.getForEntity(url, clazz);
    }

    protected <T> ResponseEntity<T> postForEntity(final String url, final Object body, final Class<T> clazz) {
        return restTemplate.postForEntity(url, body, clazz);
    }

    protected <T> ResponseEntity<T> patchForObject(final String url, final Object body, final Class<T> clazz) {
        return restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), clazz);
    }

    protected void deleteForEntity(final String url) {
        restTemplate.delete(URI.create(url));
    }
}

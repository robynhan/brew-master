package org.robynhan.com.brewmaster.resource;

import org.junit.Test;
import org.robynhan.com.brewmaster.coredomain.repository.CaseFileRepository;
import org.robynhan.com.brewmaster.coredomain.repository.CaseFolderRepository;
import org.robynhan.com.brewmaster.resource.request.AddCaseFile;
import org.robynhan.com.brewmaster.resource.request.AddCaseFolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CaseFileResourceTest extends BaseResourceTest {
    @Resource
    private CaseFolderRepository folderRepository;
    @Resource
    private CaseFileRepository fileRepository;

    @Test
    public void should_create_case_file() {
        postForEntity(getLocalUrl("/casefolder"), new AddCaseFolder("hello", null), String.class);

        String localUrl = getLocalUrl("/casefile");
        ResponseEntity<String> responseEntity = postForEntity(localUrl,
                new AddCaseFile("hello", "test.xml"), String.class);
        assertThat(responseEntity.getHeaders().getLocation().toString().contains("casefile/test.xml"), is(true));
        deleteForEntity(getLocalUrl("/casefolder") + "/hello");
    }

    @Test
    public void should_remove_case_file() {
        String localUrl = getLocalUrl("/casefile") + "/test1";

//        postForEntity(getLocalUrl("/casefolder"),new AddCaseFolder("hello", null), String.class);
        postForEntity(getLocalUrl("/casefile"), new AddCaseFile("hello", "test1"), String.class);

        ResponseEntity<String> responseEntity = getForEntity(localUrl, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        deleteForEntity(localUrl);
        ResponseEntity<String> responseEntity2 = getForEntity(localUrl, String.class);
        assertThat(responseEntity2.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void should_patch_file_content() throws IOException {
        postForEntity(getLocalUrl("/casefolder"), new AddCaseFolder("hello", null), String.class);
        postForEntity(getLocalUrl("/casefile"), new AddCaseFile("hello", "demo.json"), String.class);

        String localUrl = getLocalUrl("/casefile");

        ResponseEntity<String> responseEntity = postForEntity(localUrl + "/demo.json", "content=hello", String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        ResponseEntity<String> responseEntity2 = getForEntity(localUrl + "/demo.json", String.class);
        assertThat(responseEntity2.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity2.getBody().contains("content=hello"), is(true));
    }
}
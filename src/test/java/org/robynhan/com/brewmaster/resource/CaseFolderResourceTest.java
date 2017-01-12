package org.robynhan.com.brewmaster.resource;

import org.junit.After;
import org.junit.Test;
import org.robynhan.com.brewmaster.coredomain.repository.CaseFolderRepository;
import org.robynhan.com.brewmaster.resource.request.AddCaseFolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CaseFolderResourceTest extends BaseResourceTest {
    @Resource
    private CaseFolderRepository folderRepository;

    @After
    public void clean() {
        folderRepository.deleteAll();
    }

    @Test
    public void should_create_case_folder() {
        ResponseEntity<String> responseEntity = postForEntity(getLocalUrl("/casefolder"),
                new AddCaseFolder("hello", null), String.class);
        assertThat(responseEntity.toString().contains("casefolder/hello"), is(true));
    }

    @Test
    public void should_remove_case_folder() {
        String localUrl = getLocalUrl("/casefolder") + "/test1";
        postForEntity(getLocalUrl("/casefolder"), new AddCaseFolder("test1", null), String.class);

        ResponseEntity<String> responseEntity = getForEntity(localUrl, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        deleteForEntity(localUrl);
        responseEntity = getForEntity(localUrl, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

//    @Test
//    public void should_return_parent_and_child_in_hierarchy() {
//
//        CaseFolder sdk = new CaseFolder("sdk", null);
//        CaseFolder feature1 = new CaseFolder("feature1");
//        sdk.addChild(feature1);
//        feature1.setParent(sdk);
//
//        folderRepository.save(sdk);
//        folderRepository.save(feature1);
//
//        String localUrl = getLocalUrl("/casefolder");
////        ResponseEntity<Response> responseEntity = postForEntity(localUrl,
////                new AddCaseFolder("hello", "root"), Response.class);
////        assertThat(responseEntity.toString().contains("casefolder/hello"), is(true));
//
//        ResponseEntity<String> parentEntity = getForEntity(localUrl + "/sdk", String.class);
//        assertThat(parentEntity.getBody(), is("xx"));
//    }
}
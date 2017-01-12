package org.robynhan.com.brewmaster.coredomain.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robynhan.com.brewmaster.coredomain.model.CaseFolder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CaseCaseFolderRepositoryTest {
    @Resource
    private CaseFolderRepository repository;

    @Test
    public void should_get_folder(){
        repository.save(new CaseFolder("test", null));
        Optional<CaseFolder> folder = repository.findOneByName("test");
        assertTrue(folder.isPresent());
    }

    @Test
    public void should_get_parent(){
        CaseFolder sdk = new CaseFolder("sdk", null);
        CaseFolder feature1 = new CaseFolder("feature1");
        sdk.addChild(feature1);
        feature1.setParent(sdk);

        repository.save(sdk);
        repository.save(feature1);

        Optional<CaseFolder> sdkEntity = repository.findOneByName("sdk");
        assertTrue(sdkEntity.isPresent());
        assertThat(sdkEntity.get().getChildren().size(), is(1));

        Optional<CaseFolder> feature1Entity = repository.findOneByName("feature1");
        assertTrue(feature1Entity.isPresent());
        assertThat(feature1Entity.get().getParent(), is(sdk));
    }
}
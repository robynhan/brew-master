package org.robynhan.com.brewmaster.coredomain.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robynhan.com.brewmaster.coredomain.model.CaseFile;
import org.robynhan.com.brewmaster.coredomain.model.CaseFolder;
import org.robynhan.com.brewmaster.util.Constants;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CaseFileRepositoryTest {
    @Resource
    private CaseFileRepository fileRepository;

    @Resource
    private CaseFolderRepository folderRepository;

    @Test
    public void should_get_file() throws IOException, SQLException {
        CaseFolder caseFolder = new CaseFolder("test", null);
        folderRepository.save(caseFolder);

        File file = new File(Constants.PROJECT_FOLDER_PATH + "/src/test/resources/json/demo.json");
        CaseFile caseFile = new CaseFile("test", "{\n  \"id\": \"100\",\n  \"content\": \"hello\"\n}");
        caseFile.setCaseFolder(caseFolder);

        fileRepository.save(caseFile);
        Optional<CaseFile> retrievedFile = fileRepository.findOneByName("test");
        assertTrue(retrievedFile.isPresent());

        String content = retrievedFile.get().getContent();
        assertThat(content, is("{\n  \"id\": \"100\",\n  \"content\": \"hello\"\n}"));
    }
}
package org.robynhan.com.brewmaster.resource.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.robynhan.com.brewmaster.coredomain.model.CaseFile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddCaseFile {
    @JsonProperty
    @NotEmpty
    private String folderName;

    @JsonProperty
    @NotEmpty
    private String fileName;

    public AddCaseFile() {
    }

    public AddCaseFile(final String folderName, final String fileName) {
        this();
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public CaseFile convertToCaseFile() {
        return new CaseFile(this.fileName);
    }

    public String getFolderId() {
        return folderName;
    }
}

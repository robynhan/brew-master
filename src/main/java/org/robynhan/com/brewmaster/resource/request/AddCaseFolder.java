package org.robynhan.com.brewmaster.resource.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddCaseFolder {

    @JsonProperty
    @NotEmpty
    private String folderName;

    @JsonProperty
    @NotEmpty
    private String parent;

    public AddCaseFolder() {
    }

    public AddCaseFolder(final String folderName, final String parent) {
        this.folderName = folderName;
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    public String getFolderName() {
        return folderName;
    }
}

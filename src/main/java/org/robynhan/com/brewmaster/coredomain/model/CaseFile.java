package org.robynhan.com.brewmaster.coredomain.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@edu.umd.cs.findbugs.annotations.SuppressWarnings("DM_DEFAULT_ENCODING")
public class CaseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "folder_id")
    private CaseFolder caseFolder;

    private String name;

    private String content;

    public CaseFile() {
    }

    public CaseFile(final String name) {
        this.name = name;
    }

    public CaseFile(final String name, final String content) {
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public void setCaseFolder(final CaseFolder caseFolder) {
        this.caseFolder = caseFolder;
    }

    public CaseFolder getCaseFolder() {
        return caseFolder;
    }

    public long getId() {
        return id;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}

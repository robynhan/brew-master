package org.robynhan.com.brewmaster.coredomain.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Set;

@Entity
public class CaseFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    private CaseFolder parent;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "parent")
    private Collection<CaseFolder> children = Lists.newArrayList();

    @OneToMany(mappedBy = "caseFolder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CaseFile> caseFileSet = Sets.newHashSet();

    private String name;

    public CaseFolder() {
    }

    public CaseFolder(final String name) {
        this.name = name;
    }

    public CaseFolder(final String name, final CaseFolder parent) {
        this.name = name;
        this.parent = parent;
    }

    public CaseFolder getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Collection<CaseFolder> getChildren() {
        return children;
    }

    public void setParent(final CaseFolder parent) {
        this.parent = parent;
    }

    public void addChild(final CaseFolder child) {
        this.children.add(child);
    }

    public void addFile(final CaseFile caseFile) {
        caseFile.setCaseFolder(this);
        caseFileSet.add(caseFile);
    }
}

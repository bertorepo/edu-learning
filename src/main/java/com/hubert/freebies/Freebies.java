package com.hubert.freebies;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hubert.constants.BaseEntity;

@Entity
@Table(name = "freebie")
public class Freebies extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "freebie_name")
    private String freebieName;
    @Column(name = "freebie_size")
    private String freebieSize;
    @Column(name = "freebie_description")
    private String freebieDescription;
    @Column(name = "freebie_link")
    private String freebieLink;
    private boolean isEnabled;

    public Freebies() {
    }

    public Freebies(Long id, String freebieName, String freebieSize, String freebieDescription, String freebieLink,
            boolean isEnabled) {
        this.id = id;
        this.freebieName = freebieName;
        this.freebieSize = freebieSize;
        this.freebieDescription = freebieDescription;
        this.freebieLink = freebieLink;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFreebieName() {
        return freebieName;
    }

    public void setFreebieName(String freebieName) {
        this.freebieName = freebieName;
    }

    public String getFreebieSize() {
        return freebieSize;
    }

    public void setFreebieSize(String freebieSize) {
        this.freebieSize = freebieSize;
    }

    public String getFreebieDescription() {
        return freebieDescription;
    }

    public void setFreebieDescription(String freebieDescription) {
        this.freebieDescription = freebieDescription;
    }

    public String getFreebieLink() {
        return freebieLink;
    }

    public void setFreebieLink(String freebieLink) {
        this.freebieLink = freebieLink;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}

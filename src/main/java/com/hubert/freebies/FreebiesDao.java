package com.hubert.freebies;

import javax.validation.constraints.NotBlank;

public class FreebiesDao {

    @NotBlank(message = "Name should not be empty!")
    private String freebieName;
    @NotBlank(message = "Link should not be empty!")
    private String freebieLink;

    private String freebieDescription;
    @NotBlank(message = "Size should not be empty!")
    private String freebieSize;

    public String getFreebieName() {
        return freebieName;
    }

    public void setFreebieName(String freebieName) {
        this.freebieName = freebieName;
    }

    public String getFreebieLink() {
        return freebieLink;
    }

    public void setFreebieLink(String freebieLink) {
        this.freebieLink = freebieLink;
    }

    public String getFreebieDescription() {
        return freebieDescription;
    }

    public void setFreebieDescription(String freebieDescription) {
        this.freebieDescription = freebieDescription;
    }

    public String getFreebieSize() {
        return freebieSize;
    }

    public void setFreebieSize(String freebieSize) {
        this.freebieSize = freebieSize;
    }

}

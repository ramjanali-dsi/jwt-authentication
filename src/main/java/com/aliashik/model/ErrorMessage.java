package com.aliashik.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
    private String errorMessage;
    private Integer errorCode;
    private String documentation;

    public ErrorMessage() {
    }

    public ErrorMessage(String errorMessage, Integer errorCode, String documentation) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.documentation = documentation;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }
}

package com.swissre.bpm.mstemplate.zeebe.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MicroserviceTemplateRequest {

    private String messageText;

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String toString() {
        return "MicroserviceTemplateRequest [messageText=" + messageText + "]";
    }
}

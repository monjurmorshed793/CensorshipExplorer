package org.censorship.spring.domains;

import org.censorship.spring.enums.Status;

public class CensorshipResponse {
    private Status status;
    private String message;

    public CensorshipResponse() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

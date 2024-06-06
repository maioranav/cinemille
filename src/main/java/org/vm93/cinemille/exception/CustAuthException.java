package org.vm93.cinemille.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class CustAuthException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public CustAuthException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public CustAuthException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

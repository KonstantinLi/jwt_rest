package com.kostyali.security_jwt.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class AppException {
    private int status;
    private String message;
    private Date timestamp;

    public AppException(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}

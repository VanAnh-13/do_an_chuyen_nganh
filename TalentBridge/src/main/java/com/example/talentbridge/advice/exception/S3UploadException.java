package com.example.talentbridge.advice.exception;

public class S3UploadException extends RuntimeException {
    public S3UploadException(String message) {
        super(message);
    }
}

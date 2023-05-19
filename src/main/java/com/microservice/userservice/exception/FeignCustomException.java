package com.microservice.userservice.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class FeignCustomException extends RuntimeException{

    private static final long serialVersionUID = -127350004589811526L;

    private final int status;
    private final Map<String, Object> body;

    public FeignCustomException(int status, Map<String, Object> map) {
        this.status = status;
        this.body = map;
    }

    public FeignCustomException(String message, int status, Map<String, Object> body) {
        super(message);
        this.status = status;
        this.body = body;
    }
}

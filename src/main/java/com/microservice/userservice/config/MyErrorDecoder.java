package com.microservice.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.userservice.exception.FeignCustomException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class MyErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        String body = new BufferedReader(response.body().asReader(StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));

        Map<String, Object> map = mapper.readValue(body, Map.class);

        if(response.status() == 302) {
            return new FeignCustomException(response.status(), map);
        }
        if (response.status() >= 400 && response.status() <= 499) {
            return new FeignCustomException(response.status(), map);
        }
        if (response.status() >= 500) {
            return new FeignCustomException(response.status(), map);
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }

}

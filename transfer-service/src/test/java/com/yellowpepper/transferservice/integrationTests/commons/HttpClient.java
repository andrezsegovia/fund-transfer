package com.yellowpepper.transferservice.integrationTests.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    protected static GenericResponse latestResponse = null;

    @Autowired
    protected RestTemplate restTemplate;

    protected void get(String url) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettings requestCallback = new HeaderSettings(headers);
        final ErrorHandler errorHandler = new ErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.getHadError()) {
                return (errorHandler.getResults());
            } else {
                return (new GenericResponse(response));
            }
        });
    }

    public void post(String url, String bodyRequest) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        final HeaderSettings headerSettings = new HeaderSettings(headers);
        final ErrorHandler errorHandler = new ErrorHandler();

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);
        headerSettings.setBody(bodyRequest);
        latestResponse = restTemplate
                .execute(url, HttpMethod.POST, headerSettings, response -> {
                    if (errorHandler.getHadError()) {
                        return (errorHandler.getResults());
                    } else {
                        return (new GenericResponse(response));
                    }
                });
    }
}

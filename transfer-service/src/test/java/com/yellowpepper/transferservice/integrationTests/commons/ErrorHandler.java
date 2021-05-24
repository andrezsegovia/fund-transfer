package com.yellowpepper.transferservice.integrationTests.commons;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ErrorHandler implements ResponseErrorHandler {
    private GenericResponse results = null;
    private Boolean hadError = false;

    public GenericResponse getResults() {
        return results;
    }

    public Boolean getHadError() {
        return hadError;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        hadError = response.getRawStatusCode() >= 400;
        return hadError;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        results = new GenericResponse(response);
    }
}

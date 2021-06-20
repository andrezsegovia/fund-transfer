package com.andrezsegovia.transferservice.integrationTests.commons;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

public class GenericResponse {
    private final ClientHttpResponse theResponse;
    private final String body;

    public GenericResponse(final ClientHttpResponse response) throws IOException {
        this.theResponse = response;
        final InputStream bodyInputStream = response.getBody();
        final StringWriter stringWriter = new StringWriter();
        IOUtils.copy(bodyInputStream, stringWriter, Charset.defaultCharset());
        this.body = stringWriter.toString();
    }

    public ClientHttpResponse getTheResponse() {
        return theResponse;
    }

    public String getBody() {
        return body;
    }
}

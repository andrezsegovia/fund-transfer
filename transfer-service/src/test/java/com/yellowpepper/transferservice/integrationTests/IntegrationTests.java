package com.yellowpepper.transferservice.integrationTests;

import com.yellowpepper.transferservice.TransferServiceApplication;
import com.yellowpepper.transferservice.integrationTests.commons.ErrorHandler;
import com.yellowpepper.transferservice.integrationTests.commons.GenericResponse;
import com.yellowpepper.transferservice.integrationTests.commons.HeaderSettings;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(classes = TransferServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@PropertySource("classpath::application.properties")
public class IntegrationTests {

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

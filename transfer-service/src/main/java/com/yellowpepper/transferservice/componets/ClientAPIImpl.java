package com.yellowpepper.transferservice.componets;

import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Component
public class ClientAPIImpl implements ClientAPI {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T> T get(String url, Class<T> returnObj) {
        //TODO Enhance the GET request to be able to pass Headers
        return restTemplate.getForObject(url,returnObj);
    }

    public <T> T post(String url, Map<String, Object> body, Class<T> returnObj) {
        //TODO Enhance the GET request to be able to pass Headers
        return restTemplate.postForObject(url, body, returnObj);
    }

}

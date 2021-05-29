package com.yellowpepper.transferservice.componets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ClientAPIImpl implements ClientAPI {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T> T get(String url, Class<T> returnObj) {
        //TODO Enhance the GET request to be able to pass Headers
        return restTemplate.getForObject(url,returnObj);
    }

    @Override
    public <T> T post(String url, Map<Object, Object> body, Class<T> returnObj) {
        //TODO Enhance the GET request to be able to pass Headers
        return restTemplate.postForObject(url, body, returnObj);
    }

}

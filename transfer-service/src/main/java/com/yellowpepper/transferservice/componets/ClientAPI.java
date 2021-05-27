package com.yellowpepper.transferservice.componets;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface ClientAPI {
     <T> T get(String path, Class<T> obj);

     <T> T post(String url, Map<Object, Object> body, Class<T> returnObj);
}

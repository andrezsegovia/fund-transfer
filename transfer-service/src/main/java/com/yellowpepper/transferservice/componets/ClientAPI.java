package com.yellowpepper.transferservice.componets;

import org.springframework.stereotype.Component;

@Component
public interface ClientAPI {

     <T> T get(String path, Class<T> obj);
}

package com.andrezsegovia.transferservice.integrationTests.commons;

import com.andrezsegovia.transferservice.componets.ClientAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Map;

public class ClientAPIMockImpl implements ClientAPI {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public <T> T get(String path, Class<T> obj) {
        try {
            MapToJson.convert(resourceLoader.getResource(path).getInputStream().toString(), obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T post(String url, Map<Object, Object> body, Class<T> returnObj) {
        return null;
    }
}

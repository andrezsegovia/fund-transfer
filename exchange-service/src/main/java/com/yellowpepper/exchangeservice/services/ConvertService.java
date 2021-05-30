package com.yellowpepper.exchangeservice.services;

import org.apache.http.HttpException;

public interface ConvertService {
    Float convert(String source, String output, Float value) throws HttpException;
}

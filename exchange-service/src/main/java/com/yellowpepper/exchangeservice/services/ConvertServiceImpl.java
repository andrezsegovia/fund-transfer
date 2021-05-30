package com.yellowpepper.exchangeservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yellowpepper.exchangeservice.pojos.ConvertResponse;
import com.yellowpepper.exchangeservice.pojos.ExchangeRateAPIResponse;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;

@Service
public class ConvertServiceImpl implements ConvertService {

    @Value("${io.exchangeRateApi.api.live.url}")
    private String exchangeRateAPILiveURL;

    @Value("${io.exchangeRateApi.api.access_key}")
    private String exchangeRateAPIAccessKey;

    @Autowired
    private RestTemplate restTemplate;

    public Float convert(String source, String output, Float value) throws HttpException{
        String URL = exchangeRateAPILiveURL + "?access_key=" + exchangeRateAPIAccessKey;
        URL += "&source=" + source.toUpperCase();
        URL += "&currencies=" + output.toUpperCase();
        ObjectMapper objectMapper = new ObjectMapper();
        ExchangeRateAPIResponse exchangeRateAPIResponse = restTemplate.execute(URL, HttpMethod.GET, null, (response) -> {
            if(response.getStatusCode().is2xxSuccessful()) {
                ExchangeRateAPIResponse responseObject =
                        objectMapper.readValue(response.getBody(), ExchangeRateAPIResponse.class);
                return responseObject;
            }
            return ExchangeRateAPIResponse.builder().success(false).build();
        });
        Float newValue = exchangeRateAPIResponse.getExchangeRate() * value;
        newValue = Float.valueOf(new DecimalFormat("#0.000").format(newValue));
        return newValue;
    }
}

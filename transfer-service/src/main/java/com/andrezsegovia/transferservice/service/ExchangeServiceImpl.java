package com.andrezsegovia.transferservice.service;

import com.andrezsegovia.transferservice.commons.DecimalFormatUtil;
import com.andrezsegovia.transferservice.componets.ClientAPI;
import com.andrezsegovia.transferservice.execptions.ExchangeServiceException;
import com.andrezsegovia.transferservice.pojos.ExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Value("${local.exchange.service.url}")
    private String EXCHANGE_RATE_API_URL;

    @Autowired
    private ClientAPI clientAPI;

    @Override
    public Float exchangeUSDtoCAD(Float amount) throws ExchangeServiceException {
        final String URL = EXCHANGE_RATE_API_URL + "/exchange/convert?source=USD&output=CAD&value="+amount;
        final ExchangeResponse exchangeResponse = clientAPI.get(URL, ExchangeResponse.class);
        if(exchangeResponse.getStatus().equals("ERROR") || exchangeResponse.getErrors().length > 0) {
            throw new ExchangeServiceException();
        }
        return DecimalFormatUtil.format(exchangeResponse.getValue());
    }
}

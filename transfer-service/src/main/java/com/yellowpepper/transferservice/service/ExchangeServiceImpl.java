package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.commons.DecimalFormatUtil;
import com.yellowpepper.transferservice.componets.ClientAPI;
import com.yellowpepper.transferservice.execptions.ExchangeServiceException;
import com.yellowpepper.transferservice.pojos.ExchangeResponse;
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
        final String URL = EXCHANGE_RATE_API_URL + "/exchange?source=USD&output=CAD&value="+amount;
        final ExchangeResponse exchangeResponse = clientAPI.get(URL, ExchangeResponse.class);
        if(exchangeResponse.getStatus().equals("ERROR") || exchangeResponse.getErrors().length > 0) {
            throw new ExchangeServiceException();
        }
        return DecimalFormatUtil.format(exchangeResponse.getValue());
    }
}

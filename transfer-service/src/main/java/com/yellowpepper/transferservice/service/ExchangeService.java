package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.execptions.ExchangeServiceException;

/**
 * Provide all the required methods for exchange currencies.
 * This service calls the external API http://exchange-servie/ to make currency exchanges.
 */
public interface ExchangeService {

    Float exchangeUSDtoCAD(Float amount) throws ExchangeServiceException;
}

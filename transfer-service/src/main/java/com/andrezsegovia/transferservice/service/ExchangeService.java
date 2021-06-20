package com.andrezsegovia.transferservice.service;

import com.andrezsegovia.transferservice.execptions.ExchangeServiceException;

/**
 * Provide all the required methods for exchange currencies.
 * This service calls the external API http://exchange-servie/ to make currency exchanges.
 */
public interface ExchangeService {

    Float exchangeUSDtoCAD(Float amount) throws ExchangeServiceException;
}

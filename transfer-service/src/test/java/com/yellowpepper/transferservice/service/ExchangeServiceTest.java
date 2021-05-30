package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.SpringTestsConfig;
import com.yellowpepper.transferservice.componets.ClientAPI;
import com.yellowpepper.transferservice.execptions.ExchangeServiceException;
import com.yellowpepper.transferservice.pojos.ExchangeResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;

/**
 * Unit test for the Service {@link ExchangeService}
 */
public class ExchangeServiceTest extends SpringTestsConfig {

    @Mock
    private ClientAPI clientAPI;

    @InjectMocks
    private ExchangeService exchangeService = new ExchangeServiceImpl();

    @Value("${local.exchange.service.url}")
    private String localExchangeServiceURL;

    @Before
    public void before() {
        ReflectionTestUtils.setField(exchangeService, "EXCHANGE_RATE_API_URL",  localExchangeServiceURL);
    }

    @Test
    public void shouldExchangeUSDtoCAD() throws ExchangeServiceException {
        final Float VALUE_TO_EXCHANGE = 100.00f;
        ExchangeResponse exchangeResponseMock = ExchangeResponse.builder()
                .status("OK")
                .errors(new String[]{})
                .source("USD")
                .output("CAD")
                .value(120.745f)
                .build();

        Mockito.when(clientAPI.get(localExchangeServiceURL+"/exchange/convert?source=USD&output=CAD&value="+VALUE_TO_EXCHANGE,
                ExchangeResponse.class))
                .thenReturn(exchangeResponseMock);

        Float exchangedValue = exchangeService.exchangeUSDtoCAD(VALUE_TO_EXCHANGE);

        assertNotNull(exchangedValue);
        assertEquals(exchangedValue, exchangeResponseMock.getValue());

    }

    @Test
    public void shouldThrowExchangeServiceException() {
        final Float VALUE_TO_EXCHANGE = 100.00f;
        ExchangeResponse exchangeResponseMock = ExchangeResponse.builder()
                .status("ERROR")
                .errors(new String[]{"exchange-service-exception"})
                .source("USD")
                .output("CAD")
                .build();

        Mockito.when(clientAPI.get(localExchangeServiceURL+"/exchange/convert?source=USD&output=CAD&value="+VALUE_TO_EXCHANGE,
                ExchangeResponse.class))
                .thenReturn(exchangeResponseMock);

        ExchangeServiceException exception = assertThrows(
                ExchangeServiceException.class, () -> {
                    exchangeService.exchangeUSDtoCAD(VALUE_TO_EXCHANGE);
                });

        assertEquals("exchange-currency-error", exception.getMessage());
    }

}
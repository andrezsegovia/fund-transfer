package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.componets.ClientAPI;
import com.yellowpepper.transferservice.execptions.ExchangeServiceException;
import com.yellowpepper.transferservice.pojos.ExchangeResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Unit test for the Service {@link ExchangeService}
 */
@RunWith(MockitoJUnitRunner.class)
public class ExchangeServiceTest {

    @Mock
    private ClientAPI clientAPI;

    @InjectMocks
    private ExchangeService exchangeService = new ExchangeServiceImpl();

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

        Mockito.when(clientAPI.get("http://exchange-service/exchange?source=USD&output=CAD&value="+VALUE_TO_EXCHANGE,
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

        Mockito.when(clientAPI.get("http://exchange-service/exchange?source=USD&output=CAD&value="+VALUE_TO_EXCHANGE,
                ExchangeResponse.class))
                .thenReturn(exchangeResponseMock);

        ExchangeServiceException exception = assertThrows(
                ExchangeServiceException.class, () -> {
                    exchangeService.exchangeUSDtoCAD(VALUE_TO_EXCHANGE);
                });

        assertEquals("exchange-currency-error", exception.getMessage());
    }
}
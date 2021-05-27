package com.yellowpepper.transferservice.componets;

import com.yellowpepper.transferservice.pojos.Account;
import com.yellowpepper.transferservice.pojos.AccountResponse;
import io.cucumber.java.en_old.Ac;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.*;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientAPITest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientAPI clientAPI = new ClientAPIImpl();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldDoGetRequest() {
        Account account = AccountResponse.builder()
                .status("OK")
                .errors(new String[]{})
                .accountBalance(70000.00f).build();

        Mockito.when(restTemplate
                .getForObject("http://localhost:8082/", Account.class))
                .thenReturn(account);

        Account accountResponse = clientAPI.get("http://localhost:8082/", Account.class);

        assertEquals(account, accountResponse);
    }

    @Test
    public void shouldDoGetRequestToJSON() {
        Account account = clientAPI.get("src/test/resources/responses/account_success_response.json", Account.class);
        System.out.println(account);
    }

}
package com.yellowpepper.transferservice.componets;

import com.yellowpepper.transferservice.SpringTestsConfig;
import com.yellowpepper.transferservice.pojos.Account;
import com.yellowpepper.transferservice.pojos.AccountResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;

/**
 * Unit test for {@link ClientAPI} service
 */
public class ClientAPITest extends SpringTestsConfig {

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
    public void shouldDoPostRequest() {
        Account account = AccountResponse.builder()
                .status("OK")
                .errors(new String[]{})
                .accountBalance(70000.00f).build();
        Mockito.when(restTemplate
                .postForObject(anyString(), anyMap(), any(Class.class)))
                .thenReturn(account);
        Account accountResponse = clientAPI.post("http://localhost:8082/", new HashMap<>(), Account.class);
        assertEquals(account, accountResponse);
    }

}
package com.yellowpepper.transferservice.integrationTests;

import com.yellowpepper.transferservice.TransferServiceApplication;
import com.yellowpepper.transferservice.TransferServiceApplicationTests;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = TransferServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTests {
}

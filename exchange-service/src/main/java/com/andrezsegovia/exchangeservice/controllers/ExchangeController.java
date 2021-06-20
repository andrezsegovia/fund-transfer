package com.andrezsegovia.exchangeservice.controllers;

import com.andrezsegovia.exchangeservice.pojos.ConvertResponse;
import com.andrezsegovia.exchangeservice.services.ConvertService;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private ConvertService convertService;

    @RequestMapping(path = "/convert", method = RequestMethod.GET)
   public ResponseEntity<ConvertResponse> convertCurrency(@RequestParam("source") String source,
                                                 @RequestParam("output") String output,
                                                 @RequestParam("value") Float value) throws HttpException {
       ConvertResponse.ConvertResponseBuilder responseBuilder = ConvertResponse.builder().source(source).output(output);
       if(!source.equals("USD") || !output.equals("CAD")) {
           return new ResponseEntity<>(responseBuilder.status("ERROR")
                   .errors(new String[]{"no-allowed-currencies"})
                   .build(),HttpStatus.OK);
       }
       Float convertedValue = convertService.convert(source, output, value);
       responseBuilder.status("OK").errors(new String[]{}).value(convertedValue);
       return new ResponseEntity<>(responseBuilder.build(), HttpStatus.OK);

   }
}

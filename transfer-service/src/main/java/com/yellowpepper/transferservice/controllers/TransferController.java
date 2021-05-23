package com.yellowpepper.transferservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class TransferController {

    @GetMapping
    public ResponseEntity<String> transfer() {
        return new ResponseEntity<String>("Hello World!",HttpStatus.OK);
    }

}

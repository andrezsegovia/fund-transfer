package com.yellowpepper.accountservice.controllers;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @RequestMapping(value = "/", method =  {RequestMethod.GET, RequestMethod.POST})
    public String helloWorld() {
        return "Hello World";
    }
}

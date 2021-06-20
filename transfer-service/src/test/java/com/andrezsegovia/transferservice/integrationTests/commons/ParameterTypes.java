package com.andrezsegovia.transferservice.integrationTests.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.andrezsegovia.transferservice.pojos.TransferResponse;
import io.cucumber.java.DocStringType;

/**
 * Holds all custom Cucumber Parameter Types
 */
public class ParameterTypes {

    /**
     * Converts a docString to a {@code TransferResponse} object
     * @param docString     string to convert
     * @return TransferResponse
     * @throws JsonProcessingException
     */
    @DocStringType
    public TransferResponse transferResponse(String docString) throws JsonProcessingException {
        return new ObjectMapper().readValue(docString, TransferResponse.class);
    }

}

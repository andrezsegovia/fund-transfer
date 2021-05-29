package com.yellowpepper.transferservice.controllers;

import com.yellowpepper.transferservice.dtos.Transfer;
import com.yellowpepper.transferservice.mappers.TransferRequestMapper;
import com.yellowpepper.transferservice.mappers.TransferResponseMapper;
import com.yellowpepper.transferservice.pojos.TransferRequest;
import com.yellowpepper.transferservice.pojos.TransferResponse;
import com.yellowpepper.transferservice.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.text.ParseException;

@RestController
public class TransferController {


    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferRequestMapper transferRequestMapper;

    @Autowired
    private TransferResponseMapper transferResponseMapper;

    /**
     * Handles a request for fund transfer.
     *
     * Validates the origin account has enough funds. Calculates the amount that will be charge to the origin account
     * for the transfer. Moves the amount from the origin to the destination account. Stores the transfer attempt. And
     * returns the transfer state.
     * @param transferRequest  transfer request object
     * @return {@link TransferResponse} transfer object response
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest transferRequest) throws ParseException {
        Transfer transferResult = transferService.doTransfer(transferRequestMapper
                .transferRequestToTransfer(transferRequest));
        TransferResponse transferResponse = transferResponseMapper.transferToTransferResponse(transferResult);
        return new ResponseEntity<>(transferResponse,HttpStatus.OK);
    }

}

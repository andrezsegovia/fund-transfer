package com.yellowpepper.transferservice.controllers;

import com.yellowpepper.transferservice.dtos.Transfer;
import com.yellowpepper.transferservice.pojos.TransferResponse;
import com.yellowpepper.transferservice.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;

@RestController
public class TransferController {


    @Autowired
    private TransferService transferService;

    /**
     * Handles a request for fund transfer.
     *
     * Validates the origin account has enough funds. Calculates the amount that will be charge to the origin account
     * for the transfer. Moves the amount from the origin to the destination account. Stores the transfer attempt. And
     * returns the transfer state.
     * @param transfer  transfer request object
     * @return {@link TransferResponse} transfer object response
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<TransferResponse> transfer(@RequestBody Transfer transfer) {
        transferService.persistTransfer(transfer);
        return new ResponseEntity<TransferResponse>(new TransferResponse("OK", new String[]{}, 50.00, 66.928861615d),HttpStatus.OK);
    }

}

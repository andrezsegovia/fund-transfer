package com.andrezsegovia.transferservice.service;

import com.andrezsegovia.transferservice.dtos.Transfer;
import com.andrezsegovia.transferservice.execptions.InsufficientFundsException;

import java.text.ParseException;

public interface TransferService {

    Transfer persistTransfer(Transfer transfer);

    Transfer findTransferById(int id);

    /**
     * Perform the transfer from the origin account to the destination account. Furthermore, it persists the
     * transfer request on a database and its status.
     * @param transfer      object with the information of the transfer to perform
     * @return {@link Transfer} object with the information of the transfer an its result.
     */
    Transfer doTransfer(Transfer transfer) throws ParseException;
}

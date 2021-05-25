package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.dtos.Transfer;

public interface TransferService {

    Transfer persistTransfer(Transfer transfer);

    Transfer findTransferById(int id);
}

package com.yellowpepper.transferservice.service;

import com.yellowpepper.transferservice.daos.TransferRepository;
import com.yellowpepper.transferservice.dtos.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public Transfer persistTransfer(Transfer transfer) {
        return this.transferRepository.save(transfer);
    }

    @Override
    public Transfer findTransferById(int id) {
        return this.transferRepository.getById(id);
    }
}

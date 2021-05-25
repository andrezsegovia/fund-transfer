package com.yellowpepper.transferservice.mappers;

import com.yellowpepper.transferservice.dtos.Transfer;
import com.yellowpepper.transferservice.pojos.TransferRequest;
import org.mapstruct.Mapper;

@Mapper
public interface TransferRequestMapper {

    Transfer transferRequestToTransfer(TransferRequest transferRequest);

}

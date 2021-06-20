package com.andrezsegovia.transferservice.mappers;

import com.andrezsegovia.transferservice.dtos.Transfer;
import com.andrezsegovia.transferservice.pojos.TransferRequest;
import org.mapstruct.Mapper;

@Mapper
public interface TransferRequestMapper {

    Transfer transferRequestToTransfer(TransferRequest transferRequest);

}

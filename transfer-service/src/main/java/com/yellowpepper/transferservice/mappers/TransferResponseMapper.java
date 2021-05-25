package com.yellowpepper.transferservice.mappers;

import com.yellowpepper.transferservice.dtos.Transfer;
import com.yellowpepper.transferservice.pojos.TransferResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface TransferResponseMapper {

    @Mapping(source = "errors", target = "errors", qualifiedByName = "errorsStringToErrorsStringArray")
    TransferResponse transferToTransferResponse(Transfer transfer);

    @Named("errorsStringToErrorsStringArray")
    public static String[] errorsStringToErrorsStringArray(String errors) {
        if(errors == null || errors.isEmpty()) {
            return new String[]{};
        }
        return errors.split(",");
    }
}

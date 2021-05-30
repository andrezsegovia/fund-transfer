package com.yellowpepper.accountservice.mappers;

import com.yellowpepper.accountservice.dtos.Account;
import com.yellowpepper.accountservice.pojos.AccountRequest;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    Account accountRequestToAccount(AccountRequest accountRequest);
}

package com.andrezsegovia.accountservice.mappers;

import com.andrezsegovia.accountservice.dtos.Account;
import com.andrezsegovia.accountservice.pojos.AccountRequest;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    Account accountRequestToAccount(AccountRequest accountRequest);
}

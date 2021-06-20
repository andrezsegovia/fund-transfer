package com.andrezsegovia.accountservice.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    @NonNull
    private String account;
    @NonNull
    @JsonProperty("account_balance")
    private Float accountBalance;
}

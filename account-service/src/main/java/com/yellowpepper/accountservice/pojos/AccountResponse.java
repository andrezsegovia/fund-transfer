package com.yellowpepper.accountservice.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    @NonNull
    private String status;
    private String[] errors;
    @JsonProperty("account_balance")
    private Float accountBalance;
}

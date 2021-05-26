package com.yellowpepper.transferservice.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @JsonProperty("status")
    private String status;
    @JsonProperty("errors")
    private String[] errors;
    @JsonProperty("account_balance")
    private Float accountBalance;
}

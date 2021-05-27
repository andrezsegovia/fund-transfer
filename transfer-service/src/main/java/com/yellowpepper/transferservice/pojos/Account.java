package com.yellowpepper.transferservice.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @JsonIgnore
    private String account;
    @JsonProperty("account_balance")
    private Float accountBalance;
}

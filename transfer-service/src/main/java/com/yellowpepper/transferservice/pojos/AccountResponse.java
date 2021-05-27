package com.yellowpepper.transferservice.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse extends Account {
    @JsonProperty("status")
    private String status;
    @JsonProperty("errors")
    private String[] errors;
}

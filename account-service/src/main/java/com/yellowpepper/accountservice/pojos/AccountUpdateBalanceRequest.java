package com.andrezsegovia.accountservice.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountUpdateBalanceRequest {
    @NonNull
    private String account;
    @NonNull
    @JsonProperty("amount")
    private Float amount;
}

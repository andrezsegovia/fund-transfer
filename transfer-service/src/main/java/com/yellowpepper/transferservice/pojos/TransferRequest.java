package com.yellowpepper.transferservice.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @NonNull
    @JsonProperty(value = "amount")
    private Float amount;
    //TODO could be an Enum
    @NonNull
    @JsonProperty(value = "currency")
    private String currency;
    @NonNull
    @JsonProperty(value = "origin_account")
    private String originAccount;
    @NonNull
    @JsonProperty(value = "destination_account")
    private String destinationAccount;
    @NonNull
    @JsonProperty(value = "description")
    private String description;
}

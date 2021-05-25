package com.yellowpepper.transferservice.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    @NonNull
    @JsonProperty(value = "status")
    private String status;
    @NonNull
    @JsonProperty(value = "errors")
    private String[] errors;
    @NonNull
    @JsonProperty(value = "tax_collected")
    private Double taxCollected;
    @NonNull
    @JsonProperty(value = "CAD")
    private Double cad;
}

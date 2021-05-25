package com.yellowpepper.transferservice.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TransferResponse {

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "errors")
    private String[] errors;

    @JsonProperty(value = "tax_collected")
    private Double taxCollected;

    @JsonProperty(value = "CAD")
    private Double cad;

}

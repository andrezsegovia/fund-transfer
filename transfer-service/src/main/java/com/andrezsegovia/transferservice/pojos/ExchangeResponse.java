package com.andrezsegovia.transferservice.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Plain Old Java Object for the Exchange Service Response
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeResponse {
    @NonNull
    @JsonProperty("status")
    private String status;
    @JsonProperty("errors")
    private String[] errors;
    @JsonProperty("source")
    private String source;
    @JsonProperty("output")
    private String output;
    @JsonProperty("value")
    private Float value;
}

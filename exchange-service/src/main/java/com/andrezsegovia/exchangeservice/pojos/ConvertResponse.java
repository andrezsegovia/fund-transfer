package com.andrezsegovia.exchangeservice.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConvertResponse {
    @NonNull
    @JsonProperty("status")
    private String status;
    @JsonProperty("errors")
    private String[] errors = new String[]{};
    @JsonProperty("source")
    private String source;
    @JsonProperty("output")
    private String output;
    @JsonProperty("value")
    private Float value;
}

package com.andrezsegovia.exchangeservice.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateAPIResponse {
    private Boolean success;
    @JsonProperty("timestamp")
    private Long timeStamp;
    private String source;
    private Float exchangeRate;

    @JsonProperty("quotes")
    private void quotesUsdCad(Map<String, Object> quotes) {
        Object usdCad = quotes.get("USDCAD");
        if(usdCad != null && !String.valueOf(usdCad).isEmpty()) {
            this.exchangeRate = Float.valueOf(quotes.get("USDCAD").toString());
        } else {
            this.exchangeRate = 0.0f;
        }

    }
}


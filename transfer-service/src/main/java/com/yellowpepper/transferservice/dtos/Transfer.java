package com.yellowpepper.transferservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
@Getter @Setter
@AllArgsConstructor @ToString
public class Transfer {

    @Id @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    @JsonProperty(value = "amount")
    private int amount;

    @NotNull
    @JsonProperty(value = "currency")
    private String currency;

    @NotNull
    @JsonProperty(value = "origin_account")
    private String originAccount;

    @NotNull
    @JsonProperty(value = "destination_account")
    private String destinationAccount;

    @NotNull
    @JsonProperty(value = "description")
    private String description;

}

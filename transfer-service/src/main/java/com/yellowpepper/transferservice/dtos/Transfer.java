package com.yellowpepper.transferservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Float amount;

    @NotNull
    private String currency;

    @NotNull
    private String originAccount;

    @NotNull
    private String destinationAccount;

    @NotNull
    private String description;

    private String status;

    private String errors;

    private Float taxCollected;

    private Float cad;



}

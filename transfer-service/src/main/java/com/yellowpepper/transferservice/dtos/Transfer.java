package com.yellowpepper.transferservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


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

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }



}

package com.andrezsegovia.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    private int id;
    @NotNull
    private String account;
    @NotNull
    @Column(name = "account_balance")
    private Float accountBalance;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;
    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }
    @PostPersist
    protected void onUpdate() {
        updateDate = new Date();
    }
}

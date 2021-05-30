package com.yellowpepper.accountservice.pojos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    @NonNull
    private String account;
    private Float accountBalance;
}

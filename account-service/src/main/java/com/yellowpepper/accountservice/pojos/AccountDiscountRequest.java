package com.yellowpepper.accountservice.pojos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDiscountRequest {
    @NonNull
    private String account;
    @NonNull
    private Float discount;
}

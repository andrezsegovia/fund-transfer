package com.yellowpepper.transferservice.commons;

import org.springframework.stereotype.Component;

/**
 * Component holds the methods for tax calculus
 */
@Component
public class TaxComponent {

    public Float calculateTaxAmount(Float transferAmount, Float percentage) {
        return DecimalFormatUtil.format(transferAmount*percentage);
    }

    public Float calculateTaxPercentage(Float transferAmount) {
        if (transferAmount >= 100.0f) {
            return 0.005f;
        }
        return 0.002f;
    }


}

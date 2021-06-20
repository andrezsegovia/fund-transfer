package com.andrezsegovia.transferservice.commons;


import java.text.DecimalFormat;

public class DecimalFormatUtil {

    public static Float format(Float number) {
        return Float.valueOf(new DecimalFormat("#0.000").format(number));
    }
}

package com.yellowpepper.transferservice.componets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(JUnit4.class)
public class UtilsTests {

    @Test
    public void testSimpleDateFormant() {
        String format = "yyyy-MM-dd HH:mm:ss.SSS";
        String format2 = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        System.out.println(simpleDateFormat.format(new Date()));
        simpleDateFormat.applyPattern(format2);
        System.out.println(simpleDateFormat.format(new Date())+" 00:00:00.000");
    }

    @Test
    public void testFloatNumber() {
        System.out.println(String.format("%.3f",99f*0.002f));
        System.out.println((Float.valueOf(new DecimalFormat("#0.000").format(99f*0.002f))));
    }
}

package com.stockflow.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestData {

    public static String uniqueProductName() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "Auto Product " + timestamp;
    }
}

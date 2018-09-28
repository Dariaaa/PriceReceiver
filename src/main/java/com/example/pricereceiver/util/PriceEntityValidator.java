package com.example.pricereceiver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceEntityValidator {
    private static final Pattern pattern = Pattern.compile("(?<=[<]|[>]|[-])");


    public static boolean match(String val){
        final Matcher matcher = pattern.matcher(val);
        return matcher.find();
    }
}

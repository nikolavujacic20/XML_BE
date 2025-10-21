package com.example.backend.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatNameOfRequest(String name, String extension) {
        StringBuilder returnSeparatedWithUnderscore = new StringBuilder();
        for (String word : name.split("/")) {
            if (returnSeparatedWithUnderscore.toString().equals("")) {
                returnSeparatedWithUnderscore = new StringBuilder(word);
            } else {
                returnSeparatedWithUnderscore.append("-").append(word);
            }
        }
        returnSeparatedWithUnderscore.append(extension);
        return returnSeparatedWithUnderscore.toString();
    }

    public static String formatDate(Date date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}

package com.example.skyprivate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void log(String message) {
        System.out.println("[" + sdf.format(new Date()) + "] " + message);
    }
}

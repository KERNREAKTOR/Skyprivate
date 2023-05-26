package com.example.skyprivate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Logger {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void log(String message) {
        System.out.println("[" + sdf.format(new Date()) + "] " + message);
    }
    public static void bongaLog(String message, BongaReader bongaReader) throws IOException {
        System.out.println("[" + sdf.format(new Date()) + "] [BC] " + message);

        LocalDate heute = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filename = "C:\\bongacams\\" +bongaReader.getHistory().getDisplayName();
        File outputDir = new File(filename);

        outputDir.getAbsolutePath();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        filename = filename + "\\" + heute.format(formatter) + "_" + bongaReader.getHistory().getDisplayName() + ".log";
        PrintWriter w = new PrintWriter(new FileWriter(filename, true));
        w.println("[" + sdf.format(new Date()) + "] " + message);
        w.flush();
        w.close();
    }
}

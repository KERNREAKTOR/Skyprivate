package com.example.skyprivate;

import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
import com.example.skyprivate.CheckStatus.LiveJasmin.LiveJasminReader;
import com.example.skyprivate.CheckStatus.SkyPrivate.SkyPrivateReader;
import com.example.skyprivate.CheckStatus.StripChat.StripChatReader;

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
        String filename = "C:\\BongaCams\\" + bongaReader.getHistory().getDisplayName();
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
    public static void nameLog(String message, String performerName) throws IOException {
        System.out.println("[" + sdf.format(new Date()) + "] [BC] " + message);

        LocalDate heute = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filename = "C:\\BongaCams\\" + performerName;
        File outputDir = new File(filename);

        outputDir.getAbsolutePath();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        filename = filename + "\\" + heute.format(formatter) + "_" +performerName + ".log";
        PrintWriter w = new PrintWriter(new FileWriter(filename, true));
        w.println("[" + sdf.format(new Date()) + "] " + message);
        w.flush();
        w.close();
    }

    public static void stripLog(String message, StripChatReader stripChatReader) throws IOException {
        System.out.println("[" + sdf.format(new Date()) + "] [SC] " + message);

        LocalDate heute = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filename = "C:\\StripChat\\" + stripChatReader.getUserInfo().getUser().getUsername();
        File outputDir = new File(filename);

        outputDir.getAbsolutePath();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        filename = filename + "\\" + heute.format(formatter) + "_" + stripChatReader.getUserInfo().getUser().getUsername() + ".log";
        PrintWriter w = new PrintWriter(new FileWriter(filename, true));
        w.println("[" + sdf.format(new Date()) + "] " + stripChatReader.getUserInfo().getUser().getUsername() + " " + message);
        w.flush();
        w.close();
    }

    public static void jasminLog(String message, LiveJasminReader liveJasminReader) throws IOException {
        System.out.println("[" + sdf.format(new Date()) + "] [LJ] " + message);

        LocalDate heute = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filename = "C:\\LiveJasmin\\" + liveJasminReader.getPerformerInfo().getDisplay_name();
        File outputDir = new File(filename);

        outputDir.getAbsolutePath();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        filename = filename + "\\" + heute.format(formatter) + "_" + liveJasminReader.getPerformerInfo().getDisplay_name() + ".log";
        PrintWriter w = new PrintWriter(new FileWriter(filename, true));
        w.println("[" + sdf.format(new Date()) + "] " + message);
        w.flush();
        w.close();
    }

    public static void SkyLog(String message, SkyPrivateReader skyPrivateReader) throws IOException {
        System.out.println("[" + sdf.format(new Date()) + "] [SP] " + message);

        LocalDate heute = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filename = "C:\\SkyPrivate\\" + skyPrivateReader.getUserName();
        File outputDir = new File(filename);

        outputDir.getAbsolutePath();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        filename = filename + "\\" + heute.format(formatter) + "_" + skyPrivateReader.getUserName() + ".log";
        PrintWriter w = new PrintWriter(new FileWriter(filename, true));
        w.println("[" + sdf.format(new Date()) + "] " + message);
        w.flush();
        w.close();
    }
}

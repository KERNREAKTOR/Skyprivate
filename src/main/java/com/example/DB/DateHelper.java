package com.example.DB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    public static String getLongToDate(long unixTimestamp) {
        // Unix-Zeitstempel erhalten

        // Unix-Zeitstempel in LocalDateTime umwandeln
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(unixTimestamp, 0, java.time.ZoneOffset.UTC);

        // Datumsformat definieren
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Datum und Uhrzeit in einen String umwandeln
        return dateTime.format(formatter);
    }
    public static String getTimeNow(){
        // Aktuelles Datum und Uhrzeit erhalten
        LocalDateTime now = LocalDateTime.now();

        // Datumsformat definieren
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Datum und Uhrzeit in einen String umwandeln
        return now.format(formatter);
    }
}

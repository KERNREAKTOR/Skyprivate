package com.example.DB.BongaCams;

import com.example.skyprivate.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BongaCamsDataBase {
    private static String performerName;
    private final String url = "jdbc:sqlite:path/to/database.db";

    public BongaCamsDataBase(String performerName) {
        BongaCamsDataBase.performerName = performerName;
    }

    public void addStatus(String status) {
        // Verbindung zur Datenbank herstellen

        String tableName = "bonga_cams_performer_status_change";
        try (Connection conn = DriverManager.getConnection(url)) {

            String insertQuery = "INSERT INTO " + tableName + " (performer_name, status, timestamp) " +
                    "VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                // Aktuelles Datum und Uhrzeit erhalten
                LocalDateTime now = LocalDateTime.now();

                // Datumsformat definieren
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Datum und Uhrzeit in einen String umwandeln
                String formattedDateTime = now.format(formatter);

                stmt.setString(1, performerName);
                stmt.setString(2, status);
                stmt.setString(3, formattedDateTime);
                stmt.executeUpdate();

                //Logger.log("Performer: " + performerName + " wurde ein Eintrag in der Datenbank (" + tableName + ") hinzugefügt.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }

    public void addMessage(String message, String userName) {
        // Verbindung zur Datenbank herstellen

        String tableName = "bonga_cams_messages";
        try (Connection conn = DriverManager.getConnection(url)) {

            String insertQuery = "INSERT INTO " + tableName + " (performer_name, message, username, timestamp) " +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                // Aktuelles Datum und Uhrzeit erhalten
                LocalDateTime now = LocalDateTime.now();

                // Datumsformat definieren
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Datum und Uhrzeit in einen String umwandeln
                String formattedDateTime = now.format(formatter);

                stmt.setString(1, performerName);
                stmt.setString(2, message);
                stmt.setString(3, userName);
                stmt.setString(4, formattedDateTime);
                stmt.executeUpdate();

                //Logger.log("Performer: " + performerName + " wurde ein Eintrag in der Datenbank (" + tableName + ") hinzugefügt.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }

    public void addIncomeInfo(Integer income, String userName) {
        // Verbindung zur Datenbank herstellen

        String tableName = "bonga_cams_income";
        try (Connection conn = DriverManager.getConnection(url)) {

            String insertQuery = "INSERT INTO " + tableName + " (performer_name, amount, username, timestamp) " +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                // Aktuelles Datum und Uhrzeit erhalten
                LocalDateTime now = LocalDateTime.now();

                // Datumsformat definieren
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Datum und Uhrzeit in einen String umwandeln
                String formattedDateTime = now.format(formatter);

                stmt.setString(1, performerName);
                stmt.setInt(2, income);
                stmt.setString(3, userName);
                stmt.setString(4, formattedDateTime);
                stmt.executeUpdate();

                Logger.log("Performer: " + performerName + " wurde ein Eintrag in der Datenbank (" + tableName + ") hinzugefügt.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }
}

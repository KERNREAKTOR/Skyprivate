package com.example.DB.BongaCams;

import com.example.helpers.CurrencyHelper;
import com.example.helpers.DateHelper;
import com.example.skyprivate.Logger;

import java.sql.*;
import java.text.ParseException;

public class BongaCamsDataBase {
    private static String performerName;
    private final String url = "jdbc:sqlite:path/to/database.db";

    public BongaCamsDataBase(String performerName) {
        BongaCamsDataBase.performerName = performerName;
    }

    public void resetDataBase(){
        // Verbindung zur Datenbank herstellen
        String url = "jdbc:sqlite:path/to/database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            Logger.log("Einträge der Datenbank werden zurückgesetzt.");

            // SQL-Abfrage zum Zurücksetzen der Einträge ausführen
            //Setze die Tabelle bonga_cams_performer_status_change zurück
            String currTable="bonga_cams_performer_status_change";
            String deleteQuery = "DELETE FROM " + currTable;
            try (Statement stmt = conn.createStatement()) {
                int rowCount = stmt.executeUpdate(deleteQuery);
               Logger.log(rowCount + " Einträge inder Tabelle '"+ currTable +"' wurden zurückgesetzt.");
            }
            String resetAutoIncrementQuery = "DELETE FROM sqlite_sequence WHERE name='" + currTable + "'";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(resetAutoIncrementQuery);
                Logger.log("Auto-Increment-Wert in der Tabelle '" + currTable + " zurückgesetzt.");
            }

            //Setze die Tabelle bonga_cams_messages zurück.
            currTable="bonga_cams_messages";
            deleteQuery = "DELETE FROM " + currTable;
            try (Statement stmt = conn.createStatement()) {
                int rowCount = stmt.executeUpdate(deleteQuery);
                Logger.log(rowCount + " Einträge inder Tabelle '"+ currTable +"' wurden zurückgesetzt.");
            }
             resetAutoIncrementQuery = "DELETE FROM sqlite_sequence WHERE name='" + currTable + "'";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(resetAutoIncrementQuery);
                Logger.log("Auto-Increment-Wert in der Tabelle '" + currTable + " zurückgesetzt.");
            }

            //Setze die Tabelle bonga_cams_income zurück.
            currTable="bonga_cams_income";
            deleteQuery = "DELETE FROM " + currTable;
            try (Statement stmt = conn.createStatement()) {
                int rowCount = stmt.executeUpdate(deleteQuery);
                Logger.log(rowCount + " Einträge inder Tabelle '"+ currTable +"' wurden zurückgesetzt.");
            }
            resetAutoIncrementQuery = "DELETE FROM sqlite_sequence WHERE name='" + currTable + "'";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(resetAutoIncrementQuery);
                Logger.log("Auto-Increment-Wert in der Tabelle '" + currTable + " zurückgesetzt.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }

    }
    public void addStatus(String status, long ts) {
        // Verbindung zur Datenbank herstellen

        String tableName = "bonga_cams_performer_status_change";
        try (Connection conn = DriverManager.getConnection(url)) {

            String insertQuery = "INSERT INTO " + tableName + " (performer_name, status, timestamp) " +
                    "VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, performerName);
                stmt.setString(2, status);
                stmt.setString(3, DateHelper.getBongaLongToDate(ts));
                stmt.executeUpdate();

                //Logger.log("Performer: " + performerName + " wurde ein Eintrag in der Datenbank (" + tableName + ") hinzugefügt.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }
    public void addMessage(String message, String userName,Long ts) {
        // Verbindung zur Datenbank herstellen

        String tableName = "bonga_cams_messages";
        try (Connection conn = DriverManager.getConnection(url)) {

            String insertQuery = "INSERT INTO " + tableName + " (performer_name, message, username, timestamp) " +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, performerName);
                stmt.setString(2, message);
                stmt.setString(3, userName);
                stmt.setString(4, DateHelper.getBongaLongToDate(ts));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }

    public void addIncomeInfo(Integer income, String userName,long ts, int isBestMember,
                              String role, String displayName,String signupDate, String accessLevel,
                              int userId) {
        // Verbindung zur Datenbank herstellen

        String tableName = "bonga_cams_income";
        try (Connection conn = DriverManager.getConnection(url)) {

            String insertQuery = "INSERT INTO " + tableName + " (performer_name, amount, username, timestamp,isBestMember, role, displayName, signupDate, accessLevel, userId, euro) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

                stmt.setString(1, performerName);
                stmt.setInt(2, income);
                stmt.setString(3, userName);
                stmt.setString(4, DateHelper.getBongaLongToDate(ts));
                stmt.setInt(5, isBestMember);
                stmt.setString(6, role);
                stmt.setString(7, displayName);
                stmt.setString(8, signupDate);
                stmt.setString(9, accessLevel);
                stmt.setInt(10, userId);
                stmt.setDouble(11, CurrencyHelper.convertWithoutEuro(income * 0.05));
                stmt.executeUpdate();

                Logger.log("Performer: " + performerName + " wurde ein Eintrag in der Datenbank (" + tableName + ") hinzugefügt.");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }
}

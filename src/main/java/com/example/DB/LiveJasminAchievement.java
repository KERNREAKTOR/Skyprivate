package com.example.DB;

import java.sql.*;
import java.util.ArrayList;


public class LiveJasminAchievement {
    private Integer id;
    private Integer level;
    private Integer points;

    private String timestamp;

    public ArrayList<LiveJasminAchievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList<LiveJasminAchievement> achievements) {
        this.achievements = achievements;
    }

    private ArrayList<LiveJasminAchievement> achievements = new ArrayList<>();
    public LiveJasminAchievement(Integer id, Integer level, Integer points, String timestamp) {
        this.id = id;
        this.level = level;
        this.points = points;
        this.timestamp = timestamp;
    }

    public LiveJasminAchievement(String performerName) {
        String databasePath = "path/to/database.db";
        String tableName = "live_jasmin_achievement";


        // Verbindung zur Datenbank herstellen
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath)) {
            // SQL-Abfrage ausführen

            String selectQuery = "SELECT * FROM " + tableName + " WHERE performer_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
                stmt.setString(1, performerName);
                ResultSet resultSet = stmt.executeQuery();

                // Ergebnisse in einer Liste speichern


                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    Integer level = resultSet.getInt("level");
                    Integer points = resultSet.getInt("points");
                    String timestamp = resultSet.getString("timestamp");


                    achievements.add(new LiveJasminAchievement(id, level, points, timestamp));
                }

                // Ergebnisse ausgeben
                if (achievements.isEmpty()) {
                    System.out.println("Keine Ergebnisse für performer_name = " + performerName + " gefunden.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

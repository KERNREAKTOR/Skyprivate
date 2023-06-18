package com.example.DB.LiveJasmin;

import com.example.DB.DateHelper;
import com.example.skyprivate.CheckStatus.LiveJasmin.LiveJasminReader;
import com.example.skyprivate.Logger;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class LiveJasminDataBase {
    private LiveJasminPerformerInfo performerInfo;
    private ArrayList<LiveJasminAchievement> achievementInfo;
    private ArrayList<LiveJasminIncome> incomeInfo;
    private String performerName;

    public LiveJasminDataBase(String performerName) {
        setPerformerName(performerName);
        setPerformerInfo(new LiveJasminPerformerInfo(performerName));
        setAchievementInfo(new LiveJasminAchievement(performerName).getAchievements());
        setIncomeInfo(new LiveJasminIncome(performerName).getIncomes());
    }


    public static void main(String[] args) {
        ArrayList<LiveJasminDataBase> liveJasminDataBases = new ArrayList<>();
        liveJasminDataBases.add(new LiveJasminDataBase("LilithAnge"));
        //liveJasminDataBases.add(new LiveJasminDataBase("ValerieSins"));


        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable ljOnlineChecker = () -> {
            try {
                checkStatus(liveJasminDataBases);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        executor.scheduleAtFixedRate(ljOnlineChecker, 0, 5, TimeUnit.SECONDS);

    }

    private static void checkStatus(ArrayList<LiveJasminDataBase> liveJasminDataBases) throws IOException {
        for (LiveJasminDataBase currentPerformer : liveJasminDataBases) {

            LiveJasminReader liveJasminReader = new LiveJasminReader(currentPerformer.getPerformerName());
            boolean bFound = false;

            for (LiveJasminAchievement liveJasminAchievement : currentPerformer.getAchievementInfo()) {

                if (liveJasminAchievement.getPoints() == liveJasminReader.getPerformerInfo().getAchievement().getPoints()) {
                    bFound = true;
                    break;
                }
            }

            if (!bFound) {
                addIncomeInfo(currentPerformer.performerName,
                        liveJasminReader.getPerformerInfo().getAchievement().getPoints() -
                                currentPerformer.getAchievementInfo().get(currentPerformer.getAchievementInfo().size() - 1).getPoints());
                addAchievementInfo(currentPerformer.getPerformerName(), liveJasminReader.getPerformerInfo().getAchievement().getLevel(), liveJasminReader.getPerformerInfo().getAchievement().getPoints());

                currentPerformer.setIncomeInfo(new LiveJasminDataBase(currentPerformer.performerName).getIncomeInfo());
                currentPerformer.setAchievementInfo(new LiveJasminDataBase(currentPerformer.performerName).getAchievementInfo());
            }

            if (!Objects.equals(currentPerformer.getPerformerInfo().getChannel_image_count(), liveJasminReader.getPerformerInfo().getChannel_image_count()) ||
                    !Objects.equals(currentPerformer.getPerformerInfo().getChannel_video_count(), liveJasminReader.getPerformerInfo().getChannel_video_count()) ||
                    !Objects.equals(currentPerformer.getPerformerInfo().getStory_active_items_count(), liveJasminReader.getPerformerInfo().getStory_active_items_count())) {
                String info = " " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ";

                if (!Objects.equals(currentPerformer.getPerformerInfo().getChannel_image_count(), liveJasminReader.getPerformerInfo().getChannel_image_count())) {
                    info = info + "Channel_image_count: " + liveJasminReader.getPerformerInfo().getChannel_image_count() +
                            "(" + (currentPerformer.getPerformerInfo().getChannel_image_count() - liveJasminReader.getPerformerInfo().getChannel_image_count() + ") ");
                }

                if (!Objects.equals(currentPerformer.getPerformerInfo().getChannel_video_count(), liveJasminReader.getPerformerInfo().getChannel_video_count())) {
                    info = info + "getChannel_video_count: " + liveJasminReader.getPerformerInfo().getChannel_video_count() +
                            "(" + (currentPerformer.getPerformerInfo().getChannel_video_count() - liveJasminReader.getPerformerInfo().getChannel_video_count() + ") ");
                }

                if (!Objects.equals(currentPerformer.getPerformerInfo().getStory_active_items_count(), liveJasminReader.getPerformerInfo().getStory_active_items_count())) {
                    info = info + "getStory_active_items_count: " + liveJasminReader.getPerformerInfo().getStory_active_items_count() +
                            "(" + (currentPerformer.getPerformerInfo().getStory_active_items_count() - liveJasminReader.getPerformerInfo().getStory_active_items_count() + ") ");
                }
                Logger.log("[LJ]" + info);

                currentPerformer.updatePerformerInfo(liveJasminReader.getPerformerInfo().getChannel_image_count(),
                        liveJasminReader.getPerformerInfo().getStory_active_items_count(),
                        liveJasminReader.getPerformerInfo().getChannel_video_count());
            }
        }
    }

    public static void addAchievementInfo(String performerName, Integer level, Integer points) {
        // Verbindung zur Datenbank herstellen
        String url = "jdbc:sqlite:path/to/database.db";
        try (Connection conn = DriverManager.getConnection(url)) {

            // SQL-Abfrage zum Einfügen eines neuen Eintrags ausführen
            // Aktuelles Datum und Uhrzeit erhalten
            LocalDateTime now = LocalDateTime.now();

            // Datumsformat definieren
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Datum und Uhrzeit in einen String umwandeln
            String formattedDateTime = now.format(formatter);


            String insertQuery = "INSERT INTO live_jasmin_achievement (performer_name, level, points, timestamp) " +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, performerName);
                stmt.setInt(2, level);
                stmt.setInt(3, points);
                stmt.setString(4, formattedDateTime);
                stmt.executeUpdate();

                Logger.log("Performer: " + performerName + " wurde ein Eintrag in der Datenbank (live_jasmin_achievement) hinzugefügt.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }

    public static void addIncomeInfo(String performerName, Integer income) {
        // Verbindung zur Datenbank herstellen
        String url = "jdbc:sqlite:path/to/database.db";
        try (Connection conn = DriverManager.getConnection(url)) {

            // SQL-Abfrage zum Einfügen eines neuen Eintrags ausführen
            // Aktuelles Datum und Uhrzeit erhalten
            LocalDateTime now = LocalDateTime.now();

            // Datumsformat definieren
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Datum und Uhrzeit in einen String umwandeln
            String formattedDateTime = now.format(formatter);


            String insertQuery = "INSERT INTO live_jasmin_income (performer_name, income, timestamp) " +
                    "VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, performerName);
                stmt.setInt(2, income);
                stmt.setString(3, formattedDateTime);
                stmt.executeUpdate();

                Logger.log("Performer: " + performerName + " wurde ein Eintrag in der Datenbank (live_jasmin_income) hinzugefügt.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }

    public ArrayList<LiveJasminIncome> getIncomeInfo() {
        return incomeInfo;
    }

    public void setIncomeInfo(ArrayList<LiveJasminIncome> incomeInfo) {
        this.incomeInfo = incomeInfo;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public ArrayList<LiveJasminAchievement> getAchievementInfo() {
        return achievementInfo;
    }

    public void setAchievementInfo(ArrayList<LiveJasminAchievement> achievementInfo) {
        this.achievementInfo = achievementInfo;
    }

    public LiveJasminPerformerInfo getPerformerInfo() {
        return performerInfo;
    }

    public void setPerformerInfo(LiveJasminPerformerInfo performerInfo) {
        this.performerInfo = performerInfo;
    }

    public void updatePerformerInfo(Integer channel_image_count, Integer story_active_items_count, Integer channel_video_count) {
        // Verbindung zur Datenbank herstellen
        String url = "jdbc:sqlite:path/to/database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            // SQL-Update-Statement ausführen


            String updateQuery = "UPDATE live_jasmin_performer SET last_update = '" + DateHelper.getTimeNow() +
                    "', channel_image_count = " + channel_image_count +
                    ", story_active_items_count = " + story_active_items_count +
                    ", channel_video_count = " + channel_video_count +
                    " WHERE id = " + performerInfo.getId();
            try (Statement stmt = conn.createStatement()) {
                int rowsAffected = stmt.executeUpdate(updateQuery);
                if (rowsAffected > 0) {
                    Logger.log("[LJ] Die Daten PerformerInfo für '" + getPerformerInfo().getPerformerName() + "' wurden aktualisiert.");

                } else {
                    Logger.log("[LJ] Kein Eintrag mit ID " + performerInfo.getId() + " gefunden.");
                }
            }
        } catch (SQLException e) {
            Logger.log("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }
}

package com.example.DB;

import com.example.skyprivate.CheckStatus.LiveJasmin.LiveJasminReader;
import com.example.skyprivate.Logger;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LiveJasminDataBase {
    String performerName;
    private Integer id;
    private Integer p_id;
    private Integer channel_image_count;
    private Integer msc_id;
    private Integer story_active_items_count;
    private Integer channel_video_count;
    private String display_name;
    private String performer_id;
    private String lastupdate;

    public String getLast_activity_time() {
        return last_activity_time;
    }

    public void setLast_activity_time(String last_activity_time) {
        this.last_activity_time = last_activity_time;
    }

    private String last_activity_time;

    public String getLast_online_at() {
        return last_online_at;
    }

    public void setLast_online_at(String last_online_at) {
        this.last_online_at = last_online_at;
    }

    private String last_online_at;

    public String getBecome_online_at() {
        return become_online_at;
    }

    public void setBecome_online_at(String become_online_at) {
        this.become_online_at = become_online_at;
    }

    private String become_online_at;
private String getLongToDate(long unixTimestamp){
    // Unix-Zeitstempel erhalten

    // Unix-Zeitstempel in LocalDateTime umwandeln
    LocalDateTime dateTime = LocalDateTime.ofEpochSecond(unixTimestamp, 0, java.time.ZoneOffset.UTC);

    // Datumsformat definieren
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Datum und Uhrzeit in einen String umwandeln
    return dateTime.format(formatter);
}
    public LiveJasminDataBase(String performerName) {
        String databasePath = "path/to/database.db";
        String tableName = "live_jasmin_performer";

        try {
            // Verbindung zur Datenbank herstellen
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);

            // Überprüfen, ob der Wert bereits vorhanden ist
            String checkQuery = "SELECT COUNT(*) FROM " + tableName + " WHERE performer_name = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, performerName);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            resultSet.close();
            checkStatement.close();

            // Wenn der Wert nicht vorhanden ist, einfügen
            if (count == 0) {
                // Den aktuellen Maximalwert von 'id' abrufen
                String maxIdQuery = "SELECT MAX(id) FROM " + tableName;
                Statement maxIdStatement = connection.createStatement();
                ResultSet maxIdResultSet = maxIdStatement.executeQuery(maxIdQuery);
                int currentMaxId = 0;
                if (maxIdResultSet.next()) {
                    currentMaxId = maxIdResultSet.getInt(1);
                }
                maxIdResultSet.close();
                maxIdStatement.close();

                // Den neuen Wert in die Tabelle einfügen
                LiveJasminReader liveJasminReader = new LiveJasminReader(performerName);
                String insertQuery = "INSERT INTO " + tableName + "(id, performer_name, p_id, channel_image_count, " +
                        "msc_id, story_active_items_count, display_name, channel_video_count, become_online_at, performer_id, last_update, last_activity_time, last_online_at" +
                        ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                //id
                insertStatement.setInt(1, currentMaxId + 1);

                //performer_name
                insertStatement.setString(2, performerName);

                //p_id
                insertStatement.setInt(3, liveJasminReader.getPerformerInfo().getP_id());

                //channel_image_count
                insertStatement.setInt(4, liveJasminReader.getPerformerInfo().getChannel_image_count());

                //msc_id
                insertStatement.setInt(5, liveJasminReader.getPerformerInfo().getMsc_id());

                //story_active_items_count
                insertStatement.setInt(6, liveJasminReader.getPerformerInfo().getStory_active_items_count());

                //display_name
                insertStatement.setString(7, liveJasminReader.getPerformerInfo().getDisplay_name());

                //channel_video_count
                insertStatement.setInt(8, liveJasminReader.getPerformerInfo().getChannel_video_count());

                //become_online_at
                insertStatement.setString(9, liveJasminReader.getPerformerInfo().getBecome_online_at());

                //performer_id
                insertStatement.setString(10, liveJasminReader.getPerformerInfo().getPerformer_id());

                // last_update
                // Aktuelles Datum und Uhrzeit erhalten
                LocalDateTime now = LocalDateTime.now();

                // Datumsformat definieren
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Datum und Uhrzeit in einen String umwandeln
                String formattedDateTime = now.format(formatter);

                insertStatement.setString(11, formattedDateTime);

                //last_activity_time
                insertStatement.setString(12, getLongToDate(liveJasminReader.getPerformerInfo().getLast_activity_time()));

                //last_online_at
                insertStatement.setString(13, getLongToDate(liveJasminReader.getPerformerInfo().getLast_online_at()));
                insertStatement.executeUpdate();
                insertStatement.close();

                setId(currentMaxId + 1);
                setPerformerName(performerName);
                setP_id(liveJasminReader.getPerformerInfo().getP_id());
                setChannel_image_count(liveJasminReader.getPerformerInfo().getChannel_image_count());
                setMsc_id(liveJasminReader.getPerformerInfo().getMsc_id());
                setStory_active_items_count(liveJasminReader.getPerformerInfo().getStory_active_items_count());
                setDisplay_name(liveJasminReader.getPerformerInfo().getDisplay_name());
                setChannel_video_count(liveJasminReader.getPerformerInfo().getChannel_video_count());
                setPerformer_id(liveJasminReader.getPerformerInfo().getPerformer_id());
                setBecome_online_at(liveJasminReader.getPerformerInfo().getBecome_online_at());
                setLastupdate(formattedDateTime);
                setLast_activity_time(getLongToDate(liveJasminReader.getPerformerInfo().getLast_activity_time()));
                setLast_online_at(getLongToDate(liveJasminReader.getPerformerInfo().getLast_online_at()));

                Logger.log("Performer: " + getDisplay_name() + " wurde der Datenbank hinzugefügt.");

            } else {
                setId(getIntegerValue(tableName, "id", performerName));
                setPerformerName(performerName);
                setP_id(getIntegerValue(tableName, "p_id", performerName));
                setChannel_image_count(getIntegerValue(tableName, "channel_image_count", performerName));
                setMsc_id(getIntegerValue(tableName, "msc_id", performerName));
                setStory_active_items_count(getIntegerValue(tableName, "story_active_items_count", performerName));
                setDisplay_name(getStringValue(tableName, "display_name", performerName));
                setChannel_video_count(getIntegerValue(tableName, "channel_video_count", performerName));
                setPerformer_id(getStringValue(tableName, "performer_id", performerName));
                setBecome_online_at(getStringValue(tableName,"become_online_at",performerName));
                setLastupdate(getStringValue(tableName, "last_update", performerName));
                setLast_activity_time(getStringValue(tableName,"last_activity_time",performerName));
                setLast_online_at(getStringValue(tableName,"last_online_at",performerName));
            }
            // Verbindung schließen
            connection.close();

        } catch (SQLException e) {
            Logger.log("LiveJasminDataBase.Init");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ArrayList<LiveJasminDataBase> liveJasminDataBases = new ArrayList<>();
        liveJasminDataBases.add(new LiveJasminDataBase("LilithAnge"));
        liveJasminDataBases.add(new LiveJasminDataBase("JosephineBlaire"));

    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    public Integer getMsc_id() {
        return msc_id;
    }

    public void setMsc_id(Integer msc_id) {
        this.msc_id = msc_id;
    }

    public Integer getStory_active_items_count() {
        return story_active_items_count;
    }

    public void setStory_active_items_count(Integer story_active_items_count) {
        this.story_active_items_count = story_active_items_count;
    }

    public Integer getChannel_video_count() {
        return channel_video_count;
    }

    public void setChannel_video_count(Integer channel_video_count) {
        this.channel_video_count = channel_video_count;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getPerformer_id() {
        return performer_id;
    }

    public void setPerformer_id(String performer_id) {
        this.performer_id = performer_id;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public Integer getChannel_image_count() {
        return channel_image_count;
    }

    public void setChannel_image_count(Integer channel_image_count) {
        this.channel_image_count = channel_image_count;
    }

    private Integer getIntegerValue(String tableName, String valueToGet, String performerName) {
        String databasePath = "path/to/database.db";

        int myReturn = 0;
        try {
            // Verbindung zur Datenbank herstellen
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);

            // SQL-Abfrage, um die ID basierend auf dem Wert zu finden
            String query = "SELECT " + valueToGet + " FROM " + tableName + " WHERE performer_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, performerName);
            ResultSet resultSet = statement.executeQuery();

            // Wenn ein Ergebnis gefunden wurde, die ID auslesen
            if (resultSet.next()) {
                myReturn = resultSet.getInt(valueToGet);
            } else {
                Logger.log("Wert " + valueToGet + " konnte nicht gefunden werden.");
            }

            // Ressourcen schließen
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            Logger.log(LiveJasminDataBase.class.getSimpleName() + ".getIntegerValue: " + e.getMessage());
            e.printStackTrace();
        }
        return myReturn;
    }

    private String getStringValue(String tableName, String valueToGet, String performerName) {
        String databasePath = "path/to/database.db";

        String myReturn = null;
        try {
            // Verbindung zur Datenbank herstellen
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);

            // SQL-Abfrage, um die ID basierend auf dem Wert zu finden
            String query = "SELECT " + valueToGet + " FROM " + tableName + " WHERE performer_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, performerName);
            ResultSet resultSet = statement.executeQuery();

            // Wenn ein Ergebnis gefunden wurde, die ID auslesen
            if (resultSet.next()) {
                myReturn = resultSet.getString(valueToGet);
            } else {
                Logger.log("Wert " + valueToGet + " konnte nicht gefunden werden.");
            }

            // Ressourcen schließen
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            Logger.log(LiveJasminDataBase.class.getSimpleName() + ".getStringValue: " + e.getMessage());
            e.printStackTrace();
        }
        return myReturn;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

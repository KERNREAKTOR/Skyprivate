package com.example.DB;

import com.example.skyprivate.Logger;

import java.sql.*;

public class SQLReadHelper {
    public static Integer getIntegerValue(String tableName, String valueToGet, String performerName) {
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

    public static String getStringValue(String tableName, String valueToGet, String performerName) {
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
}

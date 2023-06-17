package com.example.DB;

import java.sql.*;
import java.util.ArrayList;

public class LiveJasminIncome {
    private ArrayList<LiveJasminIncome> incomes = new ArrayList<>();
    private Integer id;
    private String performerName;
    private Integer income;
    private String timeStamp;

    public LiveJasminIncome(Integer id, String performerName, Integer income, String timeStamp) {
        this.id = id;
        this.performerName = performerName;
        this.income = income;
        this.timeStamp = timeStamp;
    }

    public LiveJasminIncome(String performerName) {
        String databasePath = "path/to/database.db";
        String tableName = "live_jasmin_income";


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
                    Integer income1 = resultSet.getInt("income");
                    String timestamp = resultSet.getString("timestamp");

                    incomes.add(new LiveJasminIncome(id, performerName, income1, timestamp));
                    setIncomes(incomes);
                }

                // Ergebnisse ausgeben
                if (incomes.isEmpty()) {
                    System.out.println("Keine Ergebnisse für performer_name = " + performerName + " in der Tabelle '" + tableName +
                            "' gefunden.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }

    public ArrayList<LiveJasminIncome> getIncomes() {
        return incomes;
    }

    public void setIncomes(ArrayList<LiveJasminIncome> incomes) {
        this.incomes = incomes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

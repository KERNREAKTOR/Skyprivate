package com.example.helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

public class CurrencyHelper {
    public static String convertWithEuro(double value) {


        // Erstellen Sie ein NumberFormat für Währung mit Euro-Symbol
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        currencyFormat.setCurrency(Currency.getInstance("EUR"));

        // Datum und Uhrzeit in einen String umwandeln
        return currencyFormat.format(value);
    }
    public static Double convertWithoutEuro(double value) throws ParseException {


        // Erstellen Sie ein NumberFormat für das benutzerdefinierte Format
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        // Formatieren Sie den double-Wert in das benutzerdefinierte Format als String
        String formattedValue = decimalFormat.format(value);

        // Konvertieren Sie den formatierten String zurück in einen double-Wert

        return decimalFormat.parse(formattedValue).doubleValue();
    }
}

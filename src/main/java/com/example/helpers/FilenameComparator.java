package com.example.helpers;

import java.util.Comparator;

public class FilenameComparator implements Comparator<String> {

    @Override
    public int compare(String filename1, String filename2) {
        int number1 = extractNumber(filename1);
        int number2 = extractNumber(filename2);

        return Integer.compare(number1, number2);
    }

    private int extractNumber(String filename) {
        String[] parts = filename.split("_");
        if (parts.length > 1) {
            String numberString = parts[parts.length - 1].split("\\.")[0];
            try {
                return Integer.parseInt(numberString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}


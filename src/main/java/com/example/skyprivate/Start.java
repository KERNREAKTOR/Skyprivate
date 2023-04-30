package com.example.skyprivate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Start {
    public static void main(String[] args) throws InterruptedException, IOException {

        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://profiles.skyprivate.com/models/1s5qw-scofty-s.html");
        urls.add("https://profiles.skyprivate.com/models/1me83-emeralda.html");
        urls.add("https://profiles.skyprivate.com/models/kgtz-miss-cherry.html");
        urls.add("https://profiles.skyprivate.com/models/8177-miakross.html");
        urls.add("https://profiles.skyprivate.com/models/1t5mk-kristenroussel.html");
        urls.add("https://profiles.skyprivate.com/models/1tcqk-effy.html");
        urls.add("https://profiles.skyprivate.com/models/1srek-gerogina.html");
        urls.add("https://profiles.skyprivate.com/models/1sow5-ana-maria.html");
        urls.add("https://profiles.skyprivate.com/models/1p3t8-miranda-castillo.html");
        urls.add("https://profiles.skyprivate.com/models/1i0ak-emy-grey.html");

        ArrayList<String> curMode = new ArrayList<>();
        ArrayList<Long> lastSeen = new ArrayList<>();

        for (String ignored : urls) {
            curMode.add("");
            lastSeen.add(0L);
        }

        while (true) {
            ArrayList<SkyPrivateReader> users = new ArrayList<>();
            try {
                int i = 0;
                for (String url : urls) {
                    users.add(new SkyPrivateReader(url));
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

                    if (Objects.equals(users.get(i).getOnlineStatus(), "OFFLINE")) {
                        //curMode.set(i, users.get(i).getOnlineStatus());
                        if (users.get(i).getLastSeen() != 0L) {
                            if (lastSeen.get(i) < users.get(i).getLastSeen() && users.get(i).getLastSeen() < 30) {
                                lastSeen.set(i, users.get(i).getLastSeen());
//                                System.out.println(now.format(formatter) + " \uD83D\uDD34 " + users.get(i).getUserName() + " ist " + users.get(i).getOnlineStatus() +
//                                        " (" + users.get(i).getPricePerMinute() + " pro Minute )[" +
//                                        lastSeen.get(i) + " Minuten] $" + users.get(i).getLastSeen() * users.get(i).getPricePerMinute());
                            }
                        }
                    }

                    if (!curMode.get(i).equals(users.get(i).getOnlineStatus())) {
                        curMode.set(i, users.get(i).getOnlineStatus());

                        if (Objects.equals(users.get(i).getOnlineStatus(), "OFFLINE")) {
                            System.out.println(now.format(formatter) + " 🔴 " + users.get(i).getUserName() + " ist " + users.get(i).getOnlineStatus());

                            // Speichern der Informationen
                            addToDB(users.get(i).getUserName(), users.get(i).getOnlineStatus(), now.format(formatter),
                                    String.valueOf(users.get(i).getPricePerMinute()), "");
                        } else {
                            System.out.println(now.format(formatter) + " 🟢 " + users.get(i).getUserName() + " ist " + users.get(i).getOnlineStatus() + "-> "
                                    + users.get(i).getUrl() + " (" + users.get(i).getPricePerMinute() + " pro Minute)["  +
                                    lastSeen.get(i) + " Minuten] $" + lastSeen.get(i) * users.get(i).getPricePerMinute());

                            //Speichern der Informationen
                            addToDB(users.get(i).getUserName(), users.get(i).getOnlineStatus(), now.format(formatter),
                                    String.valueOf(users.get(i).getPricePerMinute()), String.valueOf(lastSeen.get(i)));
                            lastSeen.set(i, 0L);
                        }
                    }

                    i = i + 1;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Thread.sleep(10000);
        }
    }

    private static void addToDB(String username, String status, String date, String pricePerMinute, String lastSeen) throws IOException {
        LocalDate heute = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filename = "C:\\skyprivate\\" + heute.format(formatter) + "_" + username + ".log";
        PrintWriter w = new PrintWriter(new FileWriter(filename, true));
        w.println(status + "|" + date + "|" + username + "|" + pricePerMinute + "|" + lastSeen);
        w.flush();
        w.close();
    }

    private static StringBuilder getStringBuilder(String url) throws IOException {
        // HTML-Datei herunterladen
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content;
    }
}
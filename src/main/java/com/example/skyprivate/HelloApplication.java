package com.example.skyprivate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class HelloApplication{
    public static void main(String[] args) throws InterruptedException {

        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://profiles.skyprivate.com/models/1s5qw-scofty-s.html");
        urls.add("https://profiles.skyprivate.com/models/1me83-emeralda.html");

        ArrayList<String> curMode = new ArrayList<>(), lastSeen = new ArrayList<>();

        for (String ignored : urls) {
            curMode.add("❎");
            lastSeen.add("-");
        }
        // StripChatReader strip = new StripChatReader("https://profiles.skyprivate.com/models/1s5qw-scofty-s.html");
        while (true) {
            ArrayList<SkyPrivateReader> users = new ArrayList<>();
            try {
                int i = 0;
                for (String url : urls) {
                    users.add(new SkyPrivateReader(url));
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

                    if (Objects.equals(users.get(i).getOnlineStatus(), "❎")) {
                        lastSeen.set(i, users.get(i).getLastSeen());
                    }

                    if (!curMode.get(i).equals(users.get(i).getOnlineStatus())) {
                        curMode.set(i, users.get(i).getOnlineStatus());

                        if (Objects.equals(users.get(i).getOnlineStatus(), "❎")) {
                            System.out.println(now.format(formatter) + "🔴 " + users.get(i).getUserName() + " ist offline");
                            // LOG.info("🔴 " + users.get(i).getUserName() + " ist offline");
                            addToDB(users.get(i).getUserName(), users.get(i).getOnlineStatus(), now.format(formatter), users.get(i).getPricePerMinute(), "");
                        } else {
                            System.out.println(now.format(formatter) + "🟢 " + users.get(i).getUserName() + " ist online: " + users.get(i).getUrl() + " (" + users.get(i).getPricePerMinute() + " per Minute) [" + lastSeen.get(i) + "]");
                            //LOG.info("🟢 " + users.get(i).getUserName() + " ist online: " + users.get(i).getUrl() + " (" + users.get(i).getPricePerMinute() + " per Minute) [" + lastSeen.get(i) + "]");
                            addToDB(users.get(i).getUserName(), users.get(i).getOnlineStatus(), now.format(formatter), users.get(i).getPricePerMinute(), lastSeen.get(i));
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
        String filename = "C:\\skyprivate\\" + username + "_" + heute.format(formatter) + ".log";
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
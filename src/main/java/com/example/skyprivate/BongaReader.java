package com.example.skyprivate;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class BongaReader {
    public BongaHistory getHistory() {
        return history;
    }

    public void setHistory(BongaHistory history) {
        this.history = history;
    }

    private BongaHistory history;

    public BongaChat getResult() {
        return result;
    }

    public void setResult(BongaChat result) {
        this.result = result;
    }

    private BongaChat result;

    public String getPerformerURL() {
        return performerURL;
    }

    public void setPerformerURL(String performerURL) {
        this.performerURL = performerURL;
    }
    public enum PerformerStatus{
        AVAILABLE,
        FULL_PRIVATE_CHAT,
        GROUP_PRIVATE_CHAT,
        VIP_SHOW,
        OFFLINE,
        PRIVATE_CHAT;

    }

    public PerformerStatus getStatus() {
        return status;
    }

    public void setStatus(PerformerStatus status) {
        this.status = status;
    }

    private  PerformerStatus status;
    private String performerURL;
    public BongaReader(){

    }
    public BongaReader(String performerName) throws Exception {

        String bongaContent = getBongaStringBuilder(performerName);
        JSONObject jsonBonga = new JSONObject(bongaContent);

        if (Objects.equals(jsonBonga.getString("status"), "success")) {
            Gson gson = new Gson();
            setHistory(gson.fromJson(jsonBonga.getJSONObject("result").getJSONArray("history").get(0).toString(), BongaHistory.class));
            setPerformerURL("https://bongacams.com/" + performerName);
            bongaContent = String.valueOf(getStringBuilder("https://bongacams.com/get-member-chat-data?username=" + getHistory().getUsername()));
            JSONObject bongaChatShowStatusOptions = new JSONObject(bongaContent).getJSONObject("result");
            gson = new Gson();
            setResult(gson.fromJson(bongaChatShowStatusOptions.toString(),BongaChat.class));

            if(getResult().getChatShowStatusOptions().isAvailable()){
                setStatus(PerformerStatus.AVAILABLE);
            }
            if(getResult().getChatShowStatusOptions().isPrivatChat()){
                setStatus(PerformerStatus.PRIVATE_CHAT);
            }
            if(getResult().getChatShowStatusOptions().isFullPrivatChat()){
                setStatus(PerformerStatus.FULL_PRIVATE_CHAT);
            }
            if(getResult().getChatShowStatusOptions().isVipShow()){
                setStatus(PerformerStatus.VIP_SHOW);
            }
            if(getResult().getChatShowStatusOptions().isGroupPrivatChat()){
                setStatus(PerformerStatus.GROUP_PRIVATE_CHAT);
            }
            if(getResult().getChatShowStatusOptions().isOffline()){
                setStatus(PerformerStatus.OFFLINE);
            }
        }
    }

    private static StringBuilder getStringBuilder(String url) throws IOException {
        // HTML-Datei herunterladen
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("accept-language", "de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7");
        connection.setRequestProperty("cache-control", "max-age=0");
        connection.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"112\", \"Not_A Brand\";v=\"24\", \"Opera GX\";v=\"98\"");
        connection.setRequestProperty("sec-ch-ua-mobile", "?0");
        connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        connection.setRequestProperty("sec-fetch-dest", "empty");
        connection.setRequestProperty("sec-fetch-mode", "cors");
        connection.setRequestProperty("sec-fetch-site", "same-origin");
        connection.setRequestProperty("x-requested-with", "XMLHttpRequest");

        connection.setRequestProperty("Referer", "https://bongacams.com/");
        connection.setRequestProperty("referrerPolicy", "origin-when-cross-origin");

        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return content;
    }
    private static String getBongaStringBuilder(String performerName) throws Exception {

        HttpURLConnection connection = (HttpURLConnection) new URL("https://bongacams.com/history/get-models-info").openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("accept-language", "de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7");
        connection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"112\", \"Not_A Brand\";v=\"24\", \"Opera GX\";v=\"98\"");
        connection.setRequestProperty("sec-ch-ua-mobile", "?0");
        connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        connection.setRequestProperty("sec-fetch-dest", "empty");
        connection.setRequestProperty("sec-fetch-mode", "cors");
        connection.setRequestProperty("sec-fetch-site", "same-origin");
        connection.setRequestProperty("x-requested-with", "XMLHttpRequest");

        connection.setRequestProperty("Referer", "https://bongacams.com/profile/" + performerName);
        connection.setRequestProperty("referrerPolicy", "origin-when-cross-origin");

        String body = "usernames%5B%5D=" + performerName; // Request payload data
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            writer.write(body);
        }

        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            throw new Exception("Request failed with response code: " + responseCode);
        }

        connection.disconnect();

        return response.toString();
    }
}

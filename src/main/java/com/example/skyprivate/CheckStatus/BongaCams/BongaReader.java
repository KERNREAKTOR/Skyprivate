package com.example.skyprivate.CheckStatus.BongaCams;

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
    private BongaHistory history;
    private BongaChat result;
    private String performerURL;
    private String jsonHistory;
    private String jsonResult;
    private String jsonChatShowStatusOptions;
    private String jsonMiniProfileV2;
    private String jsonTipPopupOptions;
    private String jsonPrivatePopupOptions;
    private String jsonTipAfterPrivateOptions;
    private String jsonStreamOptions;
    private String jsonChatTopicOptions;
    private String jsonMemberChatNotificationSettingsOptions;
    private String jsonLoversButton;
    private String jsonMemberBalanceOptions;
    private String jsonChatOptionsNew;
    private String jsonModelNavigationOptions;
    private String jsonChatBeforeOfflineNotificationOptions;
    private String jsonChatHeaderOptions;
    private String jsonMiniProfile;
    private String jsonMiniProfileToggleOptions;
    private String jsonStylePanelOptions;

    public BongaReader() {

    }

    public BongaReader(String performerName) throws Exception {

        String bongaContent = getBongaStringBuilder(performerName);
        JSONObject jsonBonga = new JSONObject(bongaContent);
        setJsonHistory(bongaContent);
        if (Objects.equals(jsonBonga.getString("status"), "success")) {

            Gson gson = new Gson();
            setHistory(gson.fromJson(jsonBonga.getJSONObject("result").getJSONArray("history").get(0).toString(), BongaHistory.class));
            setPerformerURL("https://bongacams.com/" + performerName);

            bongaContent = String.valueOf(getStringBuilder("https://bongacams.com/get-member-chat-data?username=" + getHistory().getUsername()));
            setJsonResult(bongaContent);
            JSONObject bongaChatShowStatusOptions = new JSONObject(bongaContent).getJSONObject("result");
            //System.out.println(bongaChatShowStatusOptions.getJSONObject("miniProfileV2"));

            setJsonChatShowStatusOptions(bongaChatShowStatusOptions.getJSONObject("chatShowStatusOptions").toString());
            setJsonMiniProfileV2(bongaChatShowStatusOptions.getJSONObject("miniProfileV2").toString());
            setJsonTipPopupOptions(bongaChatShowStatusOptions.getJSONObject("tipPopupOptions").toString());
            setJsonPrivatePopupOptions(bongaChatShowStatusOptions.getJSONObject("privatePopupOptions").toString());
            setJsonStylePanelOptions(bongaChatShowStatusOptions.getJSONObject("stylePanelOptions").toString());
            setJsonMiniProfileToggleOptions(bongaChatShowStatusOptions.getJSONObject("miniProfileToggleOptions").toString());
            setJsonMiniProfile(bongaChatShowStatusOptions.getJSONObject("miniProfile").toString());
            setJsonChatHeaderOptions(bongaChatShowStatusOptions.getJSONObject("chatHeaderOptions").toString());
            setJsonChatBeforeOfflineNotificationOptions(bongaChatShowStatusOptions.getJSONObject("chatBeforeOfflineNotificationOptions").toString());
            setJsonModelNavigationOptions(bongaChatShowStatusOptions.getJSONObject("modelNavigationOptions").toString());
            setJsonChatOptionsNew(bongaChatShowStatusOptions.getJSONObject("chatOptionsNew").toString());
            setJsonMemberBalanceOptions(bongaChatShowStatusOptions.getJSONObject("memberBalanceOptions").toString());
            setJsonLoversButton(bongaChatShowStatusOptions.getJSONObject("loversButton").toString());
            setJsonMemberChatNotificationSettingsOptions(bongaChatShowStatusOptions.getJSONObject("memberChatNotificationSettingsOptions").toString());
            setJsonChatTopicOptions(bongaChatShowStatusOptions.getJSONObject("chatTopicOptions").toString());
            setJsonStreamOptions(bongaChatShowStatusOptions.getJSONObject("streamOptions").toString());
            setJsonTipAfterPrivateOptions(bongaChatShowStatusOptions.getJSONObject("tipAfterPrivateOptions").toString());

            gson = new Gson();
            setResult(gson.fromJson(bongaChatShowStatusOptions.toString(), BongaChat.class));
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

    public String getJsonChatShowStatusOptions() {
        return jsonChatShowStatusOptions;
    }

    public void setJsonChatShowStatusOptions(String jsonChatShowStatusOptions) {
        this.jsonChatShowStatusOptions = jsonChatShowStatusOptions;
    }

    public String getJsonMiniProfileV2() {
        return jsonMiniProfileV2;
    }

    public void setJsonMiniProfileV2(String jsonMiniProfileV2) {
        this.jsonMiniProfileV2 = jsonMiniProfileV2;
    }

    public String getJsonTipPopupOptions() {
        return jsonTipPopupOptions;
    }

    public void setJsonTipPopupOptions(String jsonTipPopupOptions) {
        this.jsonTipPopupOptions = jsonTipPopupOptions;
    }

    public String getJsonPrivatePopupOptions() {
        return jsonPrivatePopupOptions;
    }

    public void setJsonPrivatePopupOptions(String jsonPrivatePopupOptions) {
        this.jsonPrivatePopupOptions = jsonPrivatePopupOptions;
    }

    public String getJsonTipAfterPrivateOptions() {
        return jsonTipAfterPrivateOptions;
    }

    public void setJsonTipAfterPrivateOptions(String jsonTipAfterPrivateOptions) {
        this.jsonTipAfterPrivateOptions = jsonTipAfterPrivateOptions;
    }

    public String getJsonStreamOptions() {
        return jsonStreamOptions;
    }

    public void setJsonStreamOptions(String jsonStreamOptions) {
        this.jsonStreamOptions = jsonStreamOptions;
    }

    public String getJsonChatTopicOptions() {
        return jsonChatTopicOptions;
    }

    public void setJsonChatTopicOptions(String jsonChatTopicOptions) {
        this.jsonChatTopicOptions = jsonChatTopicOptions;
    }

    public String getJsonMemberChatNotificationSettingsOptions() {
        return jsonMemberChatNotificationSettingsOptions;
    }

    public void setJsonMemberChatNotificationSettingsOptions(String jsonMemberChatNotificationSettingsOptions) {
        this.jsonMemberChatNotificationSettingsOptions = jsonMemberChatNotificationSettingsOptions;
    }

    public String getJsonLoversButton() {
        return jsonLoversButton;
    }

    public void setJsonLoversButton(String jsonLoversButton) {
        this.jsonLoversButton = jsonLoversButton;
    }

    public String getJsonMemberBalanceOptions() {
        return jsonMemberBalanceOptions;
    }

    public void setJsonMemberBalanceOptions(String jsonMemberBalanceOptions) {
        this.jsonMemberBalanceOptions = jsonMemberBalanceOptions;
    }

    public String getJsonChatOptionsNew() {
        return jsonChatOptionsNew;
    }

    public void setJsonChatOptionsNew(String jsonChatOptionsNew) {
        this.jsonChatOptionsNew = jsonChatOptionsNew;
    }

    public String getJsonModelNavigationOptions() {
        return jsonModelNavigationOptions;
    }

    public void setJsonModelNavigationOptions(String jsonModelNavigationOptions) {
        this.jsonModelNavigationOptions = jsonModelNavigationOptions;
    }

    public String getJsonChatBeforeOfflineNotificationOptions() {
        return jsonChatBeforeOfflineNotificationOptions;
    }

    public void setJsonChatBeforeOfflineNotificationOptions(String jsonChatBeforeOfflineNotificationOptions) {
        this.jsonChatBeforeOfflineNotificationOptions = jsonChatBeforeOfflineNotificationOptions;
    }

    public String getJsonChatHeaderOptions() {
        return jsonChatHeaderOptions;
    }

    public void setJsonChatHeaderOptions(String jsonChatHeaderOptions) {
        this.jsonChatHeaderOptions = jsonChatHeaderOptions;
    }

    public String getJsonMiniProfile() {
        return jsonMiniProfile;
    }

    public void setJsonMiniProfile(String jsonMiniProfile) {
        this.jsonMiniProfile = jsonMiniProfile;
    }

    public String getJsonMiniProfileToggleOptions() {
        return jsonMiniProfileToggleOptions;
    }

    public void setJsonMiniProfileToggleOptions(String jsonMiniProfileToggleOptions) {
        this.jsonMiniProfileToggleOptions = jsonMiniProfileToggleOptions;
    }

    public String getJsonStylePanelOptions() {
        return jsonStylePanelOptions;
    }

    public void setJsonStylePanelOptions(String jsonStylePanelOptions) {
        this.jsonStylePanelOptions = jsonStylePanelOptions;
    }

    public BongaHistory getHistory() {
        return history;
    }

    public void setHistory(BongaHistory history) {
        this.history = history;
    }

    public BongaChat getResult() {
        return result;
    }

    public void setResult(BongaChat result) {
        this.result = result;
    }

    public String getPerformerURL() {
        return performerURL;
    }

    public void setPerformerURL(String performerURL) {
        this.performerURL = performerURL;
    }

    public String getJsonHistory() {
        return jsonHistory;
    }

    public void setJsonHistory(String jsonHistory) {
        this.jsonHistory = jsonHistory;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }
}

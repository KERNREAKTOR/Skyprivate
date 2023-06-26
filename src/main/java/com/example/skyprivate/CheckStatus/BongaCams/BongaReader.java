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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class BongaReader {
    private BongaHistory history;
    private String vid;
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
    private static final String URL = "https://bongacams.com/tools/amf.php";

    public static void main(String[] args) {
        String model = "milkssweeet"; // Replace with the desired model

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(URL);

        // Set headers
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");

        // Set form data
        List<NameValuePair> formData = new ArrayList<>();
        formData.add(new BasicNameValuePair("method", "getRoomData"));
        formData.add(new BasicNameValuePair("args[]", model));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formData));

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseData = EntityUtils.toString(entity);
                // Process the response data as needed
                System.out.println(responseData);
            } else {
                System.out.println("No response data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public BongaReader(String performerName) throws Exception {
        String bongaContent = getBongaStringBuilder(performerName);
        JSONObject jsonBonga = new JSONObject(bongaContent);
        setJsonHistory(bongaContent);
        if (Objects.equals(jsonBonga.getString("status"), "success")) {

            Gson gson = new Gson();
            setHistory(gson.fromJson(jsonBonga.getJSONObject("result").getJSONArray("history").get(0).toString(), BongaHistory.class));
            setPerformerURL("https://bongacams.com/" + performerName);

            bongaContent = String.valueOf(getStringBuilder("https://bongacams.com/get-member-chat-data?username=" + performerName));
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
//            bongaContent = String.valueOf(getStringBuilder("https://bongacams.com/tools/listing_v3.php?chathost=" + getHistory().getDisplayName() +"&_nav=1"));
//            jsonBonga = new JSONObject(bongaContent);
//            //if(getHistory().isOnline()){
//                setVid(jsonBonga.getJSONObject("nav").getJSONObject("current").getString("vsid"));
//            //}
//
//            System.out.println(bongaContent);
        }
    }

    public static boolean isFileAvailable(String fileURL) {
        try {
            URL url = new URL(fileURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");

            // Überprüfe den HTTP-Statuscode
            int responseCode = connection.getResponseCode();

            // Ein Statuscode von 200 bedeutet, dass die Datei verfügbar ist
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVideoUrl() throws IOException {

        String vSID = null;
        //https://live-edge73.bcvcdn.com/hls/stream_Mashulya29/public-aac/stream_Mashulya29/l_1731625_2520000_1260.ts
        if (getHistory().isOnline()) {
            String bongaContent = String.valueOf(getStringBuilder("https://bongacams.com/tools/listing_v3.php?chathost=" + getHistory().getUsername() + "&_nav=1"));
            JSONObject jsonBonga = new JSONObject(bongaContent);
            vSID = "https://live-edge" + jsonBonga.getJSONObject("nav").getJSONObject("current").getString("vsid") +
                    ".bcvcdn.com/hls/stream_" + getHistory().getUsername() + "/public-aac/stream_" +
                    getHistory().getUsername();
        }
        return vSID;
    }
    public String getPlayList() throws IOException {

        String vSID = null;
        //https://live-edge73.bcvcdn.com/hls/stream_Mashulya29/public-aac/stream_Mashulya29/l_1731625_2520000_1260.ts
        if (getHistory().isOnline()) {
            String bongaContent = String.valueOf(getStringBuilder("https://bongacams.com/tools/listing_v3.php?chathost=" + getHistory().getUsername() + "&_nav=1"));
            JSONObject jsonBonga = new JSONObject(bongaContent);
            vSID = "https://live-edge" + jsonBonga.getJSONObject("nav").getJSONObject("current").getString("vsid") +
                    ".bcvcdn.com/hls/stream_" + getHistory().getUsername() + "/playlist.m3u8";
        }
        return vSID;
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

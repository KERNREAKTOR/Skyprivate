package com.example.skyprivate;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StripChatReader {
    private String url;
    private String snapshotURL;
    private PerformerMode performerMode;
    private StripChatUser userInfo;
    private StripChatCam camInfo;

    public StripChatReader(String username) throws Exception {
        // Preview Image
        //https://img.strpst.com/thumbs/snapshotTimestamp/id_webp
        //https://img.strpst.com/thumbs/1684348230/70251310_webp
        String jsonCamUrl = "https://de.stripchat.com/api/front/v2/models/username/" + username + "/cam";
        String jsonChatUrl = "https://de.stripchat.com/api/front/v2/models/username/" + username + "/chat";
        String jsonBonga = getBongaStringBuilder("https://bongacams.com/history/get-models-info").toString();
        JSONObject jsonCam = new JSONObject(getStringBuilder(jsonCamUrl).toString());

        JSONObject users = jsonCam.getJSONObject("user").getJSONObject("user");
        JSONObject firstUsers = jsonCam.getJSONObject("user");
        JSONObject firstCam = jsonCam.getJSONObject("cam");
        Gson gson = new Gson();

        setUserInfo(gson.fromJson(firstUsers.toString(), StripChatUser.class));
        setCamInfo(gson.fromJson(firstCam.toString(), StripChatCam.class));

        setSnapshotURL("https://img.strpst.com/thumbs/" + getUserInfo().getUser().getSnapshotTimestamp() + "/" + getUserInfo().getUser().getId() + "_webp");


        setUrl("https://de.stripchat.com/" + getUserInfo().getUser().getUsername());

        switch (users.getString("status")) {
            case "public" -> setPerformerMode(PerformerMode.PUBLIC);
            case "p2p" -> setPerformerMode(PerformerMode.EXCLUSIVE_PRIVATE);
            case "virtualPrivate" -> setPerformerMode(PerformerMode.VIRTUAL_PRIVATE);
            case "private" -> setPerformerMode(PerformerMode.PRIVATE);
            case "groupShow" -> setPerformerMode(PerformerMode.GROUP_SHOW);
            case "idle" -> setPerformerMode(PerformerMode.IDLE);
            case "off" -> setPerformerMode(PerformerMode.OFFLINE);
            default -> {
                setPerformerMode(PerformerMode.UNKNOWN);
                Logger.log("Unbekannter status: " + users.getString("status"));
            }
        }
    }

    private static String getBongaStringBuilder(String url) throws Exception {

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
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

        connection.setRequestProperty("Referer" , "https://bongacams.com/profile/belladonna");
        connection.setRequestProperty("referrerPolicy", "origin-when-cross-origin");

        String body = "usernames%5B%5D=belladonna"; // Request payload data
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

    private static StringBuilder getStringBuilder(String url) throws IOException {
        // HTML-Datei herunterladen
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestProperty("authority", "stripchat.com");
        con.setRequestProperty("dnt", "1");
        con.setRequestProperty("upgrade-insecure-requests", "1");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
        con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        con.setRequestProperty("sec-fetch-site", "none");
        con.setRequestProperty("sec-fetch-mode", "navigate");
        con.setRequestProperty("accept-language", "en-US,en;q=0.9");
        con.setRequestMethod("GET");
        //con.setConnectTimeout(10000);
        //con.setReadTimeout(10000);
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

    public StripChatCam getCamInfo() {
        return camInfo;
    }

    public void setCamInfo(StripChatCam camInfo) {
        this.camInfo = camInfo;
    }

    public StripChatUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(StripChatUser userInfo) {
        this.userInfo = userInfo;
    }

    public void downloadSnapshot() throws IOException {
        if (isFileAvailable(getSnapshotURL())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String folderPath = "H:\\Stripchat\\" + getUserInfo().getUser().getUsername() + "\\" + sdf.format(new Date());
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs(); // Erstelle den Ordner und alle übergeordneten Ordner
            }
            String fileName = getUserInfo().getUser().getSnapshotTimestamp() + ".webp";
            String filePath = folderPath + "/" + fileName;

            String fileURL = getSnapshotURL();
            URL url = new URL(fileURL);
            URLConnection connection = url.openConnection();

            try (
                    InputStream in = connection.getInputStream();
                    FileOutputStream out = new FileOutputStream(filePath)
            ) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    public String getSnapshotURL() {
        return snapshotURL;
    }

    public void setSnapshotURL(String snapshotURL) {
        this.snapshotURL = snapshotURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PerformerMode getPerformerMode() {
        return performerMode;
    }

    public void setPerformerMode(PerformerMode performerMode) {
        this.performerMode = performerMode;
    }

    enum PerformerMode {
        PRIVATE,
        VIRTUAL_PRIVATE,
        EXCLUSIVE_PRIVATE,
        GROUP_SHOW,
        IDLE,
        PUBLIC,
        OFFLINE,
        UNKNOWN
    }

}

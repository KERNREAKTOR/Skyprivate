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

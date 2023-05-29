package com.example.skyprivate.CheckStatus.LiveJasmin;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LiveJasminReader {
    public LiveJasminData getPerformerInfo() {
        return performerInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
    public void setPerformerInfo(LiveJasminData performerInfo) {
        this.performerInfo = performerInfo;
    }

    private LiveJasminData performerInfo;
   public LiveJasminReader(String performerName) throws IOException {
       String stringLJ = getLJStringBuilder("https://www.livejasmin.com/de/flash/get-performer-details/" + performerName).toString();
       JSONObject jsonJasmin = new JSONObject(stringLJ).getJSONObject("data");

       Gson gson = new Gson();
       setPerformerInfo(gson.fromJson(jsonJasmin.toString(), LiveJasminData.class));
       setUrl("https://www.livejasmin.com/" + performerName);
    }
    private static StringBuilder getLJStringBuilder(String url) throws IOException {
        // HTML-Datei herunterladen
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("GET");

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

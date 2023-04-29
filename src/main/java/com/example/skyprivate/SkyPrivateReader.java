package com.example.skyprivate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SkyPrivateReader {
    private String onlineStatus;
    private String userName;
    private String lastSeen;
    private String pricePerMinute;
    private String skypeID;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public SkyPrivateReader(String url) {
        StringBuilder skyPrivateContent = null;
        setUrl(url);
        try {
            skyPrivateContent = getStringBuilder(url);

        } catch (Exception e) {
            setOnlineStatus("Unbekannt");
            setUserName("Unbekannt");
            System.out.println(e.getMessage());
        }
        assert skyPrivateContent != null;
        setOnlineStatus("Unbekannt");
        if (skyPrivateContent.toString().contains("profile-picture__status--online")) {
            setOnlineStatus("✅");
        }
        if (skyPrivateContent.toString().contains("profile-picture__status--offline")) {
            setOnlineStatus("❎");
        }
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

        } catch (Exception e) {
            setOnlineStatus("Unbekannt");
            setUserName("Unbekannt");
            System.out.println(e.getMessage());
        }

        // Wählen Sie das Element mit der Klasse "profile-meta__heading__item ignoreTranslate" aus
        assert doc != null;
        setUserName(getResult(doc, "span.profile-meta__heading__item.ignoreTranslate"));
        setSkypeID(getResult(doc, "span.skype-id__id"));
        setPricePerMinute(getResult(doc, "p.profile-meta__price span"));
        setLastSeen(getResult(doc, "span.page-section__heading__tag__text"));
    }

    private static String getResult(Document doc, String filter) {
        Elements elements;
        elements = doc.select(filter);
        String result = null;

        // Überprüfen Sie, ob das Element gefunden wurde, und extrahieren Sie den Namen
        if (elements.size() > 0) {
            Element element = elements.first();
            assert element != null;
            result = element.text();
        }
        return result;
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

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(String pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public String getSkypeID() {
        return skypeID;
    }

    public void setSkypeID(String skypeID) {
        this.skypeID = skypeID;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

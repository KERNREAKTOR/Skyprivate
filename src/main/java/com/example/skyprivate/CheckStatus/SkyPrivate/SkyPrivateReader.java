package com.example.skyprivate.CheckStatus.SkyPrivate;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class SkyPrivateReader {
    private String onlineStatus;
    private String userName;
    private String lastSeen;
    private String pricePerMinute;
    private String skypeID;
    private String url;

    public SkyPrivateReader(String url) {
//        String htm = getHtmlContent("https://www.livejasmin.com/de/frauen/#!chat/AriannaNastya");
        setUrl(url);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

        } catch (Exception e) {
            setOnlineStatus("Unbekannt");
            setUserName("Unbekannt");
            setSkypeID("Unbekannt");
            setPricePerMinute(null);
            setLastSeen("-");
            System.out.println(e.getMessage());
        }

        // Wählen Sie das Element mit der Klasse "profile-meta__heading__item ignoreTranslate" aus
        assert doc != null;
        setUserName(getResult(doc, "span.profile-meta__heading__item.ignoreTranslate"));
        setSkypeID(getResult(doc, "span.skype-id__id"));
        setPricePerMinute(getResult(doc, "p.profile-meta__price span"));
        setLastSeen(getResult(doc, "span.page-section__heading__tag__text"));
        setOnlineStatus(getResult(doc, "span.profile-picture__status"));

        //Profilinformationen
        //Age 18 Sex Female Location Medellin, Colombia Sexual orientation Bisexual Speaks English Eyes Black Hair Black Constitution Athletic Height 168 cm Weight 52 Kg Twitter Scofty_s Category Regular Tags anal, female, bigass, pretty, pink, glamour, soft, kitty
        String test = getResult(doc, "div.profile__dl");
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

    private static String getHtmlContent(String url) throws IOException {
        String cookieHeader = "unique_visitor_id=7d000992-6045-4536-a8ea-69005603dea3; expires=Wed, 31 May 2023 06:55:20 GMT; path=/; domain=.livejasmin.com; secure; SameSite=Lax";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            // Setzen des Cookie-Header
            httpGet.setHeader("Cookie", cookieHeader);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        }
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getLastSeen() {
        //String str = "Last seen 11 minutes ago";
        int lastTime = Integer.parseInt(lastSeen.replaceAll("[^0-9]", ""));

        Duration duration = Duration.ofMinutes(lastTime);

        if (lastSeen.contains("seconds")) {
            duration = Duration.ofMinutes(1);
        }
        if (lastSeen.contains("minutes")) {
            duration = Duration.ofMinutes(lastTime);
        }
        if (lastSeen.contains("hours")) {
            duration = Duration.ofHours(lastTime);
        }
        if (lastSeen.contains("days")) {
            duration = Duration.ofDays(lastTime);
        }

        return duration.toMinutes();
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Double getPricePerMinute() {
        String str = pricePerMinute;
        double value = 0.0;

        if (str != null) {
            str = str.replaceAll("[^\\d.]+", ""); // Entfernen von allem außer Zahlen und Punkten
            value = Double.parseDouble(str);
        }

        return value;
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

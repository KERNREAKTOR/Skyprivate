package com.example.DB;

import com.example.DB.BongaCams.BongaCamsDataBase;
import com.example.skyprivate.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SecureWebSocketClientExample {
    private static final String WEBSOCKET_URL = "wss://chat05.bcccdn.com/websocket";
    private final String performerName = "scoftyss";
    //private final String performerName = "lolypop19";
    private int currId;
    private WebSocketClient client;

    public static void main(String[] args) {
        SecureWebSocketClientExample reader = new SecureWebSocketClientExample();
        reader.start();
    }

    private void start() {
        connectToWebSocket();

        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            input = scanner.nextLine();
            if (client.isOpen()) {
                client.send(input);
            }
        } while (!input.equalsIgnoreCase("exit"));

        client.close();
        scanner.close();
    }

    private void connectToWebSocket() {
        try {
            client = new WebSocketClient(new URI(WEBSOCKET_URL)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    // WebSocket connection is successfully established

                    Logger.log("Connected to WebSocket!");
                    Info workerData = new Info(performerName); // Set your worker data here

                    String chathost = workerData.getChathost();
                    String username = workerData.getUsername();
                    String displayName = workerData.getDisplayName();
                    String location = workerData.getLocation();
                    boolean isRu = workerData.isRu();
                    String dataKey = workerData.getDataKey();
                    String joinRoomMessage = String.format("{\"id\":1,\"name\":\"joinRoom\",\"args\":[\"%s\",{\"username\":\"%s\",\"displayName\":\"%s\",\"location\":\"%s\",\"chathost\":\"%s\",\"isRu\":%b,\"isPerformer\":false,\"hasStream\":false,\"isLogged\":false,\"isPayable\":false,\"showType\":\"public\"}],\"%s\"}",
                            chathost, username, displayName, location, chathost, isRu, dataKey);

                    joinRoomMessage = "{\"id\":1,\"name\":\"joinRoom\",\"args\":[\""
                            + chathost + "\",{\"username\":\"" +
                            username + "\",\"displayName\":\"" +
                            displayName + "\",\"location\":\"" +
                            location + "\",\"chathost\":\"" +
                            chathost + "\",\"isRu\":" +
                            isRu + ",\"isPerformer\":false,\"hasStream\":false,\"isLogged\":false,\"isPayable\":false,\"showType\":\"public\"},\"" +
                            dataKey + "\"]}";

                    send(joinRoomMessage);
                    currId = 0;
                    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                    Runnable bongaChecker = () -> {
                        try {
                            if (currId != 0) {
                                send("{\"id\":" + currId + ",\"name\":\"ping\"}");
                                Logger.log("Ping current Id :" + currId);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    };
                    executor.scheduleAtFixedRate(bongaChecker, 0, 2, TimeUnit.MINUTES);
                }

                @Override
                public void onMessage(String message) {
                    // Handle incoming messages
                    JSONObject jsonObject = new JSONObject(message);

                    if (jsonObject.has("id")) {
                        if (jsonObject.getInt("id") == 1) {
                            if (jsonObject.getJSONObject("result").has("audioAvailable")) {
                                if (jsonObject.getJSONObject("result").getBoolean("audioAvailable")) {
                                    send("{\"id\":2,\"name\":\"ChatModule.connect\",\"args\":[\"public-chat\"]}");
                                    Logger.log("Mit dem chat von '" + performerName + "' verbunden");
                                    currId = jsonObject.getInt("id") + 1;
                                }else{
                                    Logger.log(performerName + " ist nicht verfügbar");
                                }
                            } else {

                                currId = jsonObject.getInt("id") + 1;
                            }
                        }

                    }

                    if (jsonObject.has("result")) {
                        if (!Objects.equals(jsonObject.get("result").toString(), "null")) {
                            if (jsonObject.getJSONObject("result").has("tk")) {
                                //String unKingName = jsonObject.getJSONObject("result").getJSONObject("tk").getString("un");
                                String dnKingName = jsonObject.getJSONObject("result").getJSONObject("tk").getString("dn");
                                int amount = jsonObject.getJSONObject("result").getJSONObject("tk").getInt("amount");
                                Logger.log("Der aktuelle König ist :" + dnKingName + " mit " + amount + " Token");

//
                                //System.out.println(jsonObject.getJSONObject("result").getJSONObject("tk").getInt("amount"));
                            }
                            if (jsonObject.getJSONObject("result").has("status")) {

                                BongaCamsDataBase dataBase = new BongaCamsDataBase(performerName);
                                dataBase.addStatus(jsonObject.getJSONObject("result").getString("status"));

                                switch (jsonObject.getJSONObject("result").getString("status")) {
                                    case "offline" -> Logger.log(performerName + " ist Offline");
                                    case "public" -> Logger.log(performerName + " ist Öffentlich");
                                    case "away" -> Logger.log(performerName + " ist AFK");
                                    default ->
                                            Logger.log("Statuts unbekannt: " + jsonObject.getJSONObject("result").getString("status"));
                                }
                            }
                        }


                    }

                    if (jsonObject.has("type")) {
                        String ServerMessageEvent = jsonObject.getString("type");
                        switch (ServerMessageEvent) {
                            case "ServerMessageEvent:DELETE_MESSAGE" -> System.out.println("DELETE_MESSAGE");
                            case "ServerMessageEvent:INCOMING_NOTICE" -> { //System.out.println("INCOMING_NOTICE");
                            }
                            case "ServerMessageEvent:INCOMING_TIP" -> {
                                int amount = jsonObject.getJSONObject("body").getInt("a");
                                String username = jsonObject.getJSONObject("body").getJSONObject("f").getString("username");
                                BongaCamsDataBase dataBase = new BongaCamsDataBase(performerName);
                                dataBase.addIncomeInfo(amount, username);
                                Logger.log(username + " hat " + amount + " Token gegeben.");
                            }
                            case "ServerMessageEvent:PERFORMER_STATUS_CHANGE" -> {

                                switch (jsonObject.getString("body")) {
                                    case "offline" -> Logger.log(performerName + " ist offline.");
                                    default ->
                                            Logger.log("Unbekannter PERFORMER_STATUS_CHANGE: " + jsonObject.getString("body"));
                                }
                            }
                            case "ServerMessageEvent:ROOM_CLOSE" ->
                                    Logger.log("Raum geschlossen: " + jsonObject.getJSONObject("body").getString("code"));
                            case "ServerMessageEvent:PERFORMER_AUDIO_STATE:ROOM_CLOSE" ->
                                    Logger.log("PERFORMER_AUDIO_STATE");

                            case "ServerMessageEvent:CHAT_INCOMING_MESSAGE" -> {
                                if (!Objects.equals(jsonObject.getJSONObject("body").getString("message"), "tip_menu")) {
                                    BongaCamsDataBase dataBase = new BongaCamsDataBase(performerName);
                                    String username = jsonObject.getJSONObject("body").getJSONObject("author").getString("displayName");
                                    String userMessage = jsonObject.getJSONObject("body").getString("message");
                                    dataBase.addMessage(userMessage, username);
                                    Logger.log(username + " :" + userMessage);
                                }
                            }
                            case "ServerMessageEvent:TOPIC_CHANGE" -> Logger.log("TOPIC_CHANGE");

                            default -> System.out.println(ServerMessageEvent);
                        }
                    }
                    //System.out.println("Received message: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    // WebSocket connection is closed
                    Logger.log("WebSocket connection closed.");
                    client.reconnect();
                }

                @Override
                public void onError(Exception ex) {
                    // Handle any errors that occur
                    ex.printStackTrace();
                }
            };

            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    static class Info {
        private String room;
        private String server;
        private String proxy;
        private String chathost;
        private String username;
        private String displayName;
        private String location;
        private boolean isRu;
        private String dataKey;
        private String params;

        Info(String model) {


            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("https://bongacams.com/tools/amf.php");

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
                    JSONObject performerInfo = new JSONObject(responseData);
                    chathost = performerInfo.getJSONObject("userData").getString("chathost");
                    username = performerInfo.getJSONObject("userData").getString("username");
                    location = performerInfo.getJSONObject("userData").getString("location");
                    displayName = performerInfo.getJSONObject("userData").getString("displayName");
                    isRu = performerInfo.getJSONObject("userData").getBoolean("isRu");
                    dataKey = performerInfo.getJSONObject("localData").getString("dataKey");
                    // Process the response data as needed
                    //System.out.println(responseData);
                } else {
                    System.out.println("No response data");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Getters and setters for the fields

        public String getChathost() {
            return chathost;
        }

        public String getUsername() {
            return username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getLocation() {
            return location;
        }

        public boolean isRu() {
            return isRu;
        }

        public String getDataKey() {
            return dataKey;
        }

    }
}

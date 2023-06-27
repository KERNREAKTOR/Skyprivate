package com.example.DB;

import com.example.DB.BongaCams.BongaCamsDataBase;
import com.example.helpers.CurrencyHelper;
import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
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

import java.io.IOException;
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
    private static final String performerName = "rocksbabiess";
    //private static final String performerName = "scoftyss";
    private Boolean currStatus = null;
    private int currId;
    private WebSocketClient client;

    public static void main(String[] args) {

        //##########################
        //# Tabellen zurücksetzten #
        //##########################
        new BongaCamsDataBase(performerName).resetDataBase();

        SecureWebSocketClientExample reader = new SecureWebSocketClientExample();
        reader.start();
    }

    private static void incomingTip(JSONObject jsonObject, long timestamp) {
        //{"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":10,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":false,"displayName":"Zubastikkkk","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":false,"showType":"public","isBanned":false,"hasProfile":true,"messageSenderSettings":"all","signupDate":"01/25/2016","accessLevel":"unlimited","avatarUrl":"/images/avatars/40/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":20328772,"isPerformer":false,"isMcm":false,"chathost":"Aroused1101","isRu":true,"isMuted":false,"username":"Zubastikkkk","hasStream":false,"isPayable":true}},"ts":1687465677028}
        int amount = jsonObject.getJSONObject("body").getInt("a");
        String username = jsonObject.getJSONObject("body").getJSONObject("f").getString("username");
        int isBestMember;
        if (jsonObject.getJSONObject("body").getJSONObject("f").getBoolean("isBestMember")) {
            isBestMember = 1;
        } else {
            isBestMember = 0;
        }
        String role = jsonObject.getJSONObject("body").getJSONObject("f").getString("role");
        String displayName = jsonObject.getJSONObject("body").getJSONObject("f").getString("displayName");
        String signupDate = jsonObject.getJSONObject("body").getJSONObject("f").getString("signupDate");
        String accessLevel = jsonObject.getJSONObject("body").getJSONObject("f").getString("accessLevel");
        int userId = jsonObject.getJSONObject("body").getJSONObject("f").getInt("userId");

        BongaCamsDataBase dataBase = new BongaCamsDataBase(performerName);
        dataBase.addIncomeInfo(amount, username, timestamp, isBestMember, role, displayName, signupDate, accessLevel, userId);
        Logger.log(username + " hat " + amount + "(" + CurrencyHelper.convertWithEuro(amount * 0.05) + " €) Token gegeben.");
    }

    private static void chatIncomingMessage(JSONObject jsonObject, long timestamp) {
        //{"type":"ServerMessageEvent:CHAT_INCOMING_MESSAGE","body":{"author":{"signupDate":"05/01/2015","role":"model","avatarUrl":"/00c/31b/027/d2609ec6b66b5d636efec5838891d37b_avatars.jpg","messageStyle":{"color":"#ff00ff"},"displayName":"_LeraX_","friendRequestSettings":"all","isLogged":true,"sexType":"","userId":12795039,"isPerformer":true,"chathost":"Aroused1101","showType":"public","isRu":true,"hasProfile":true,"username":"Aroused1101","hasStream":true,"isPayable":false},"own":false,"id":"181","type":"public-chat","message":"Thank you  ❤ mmm...","l":"cb:tip-response:ml-en:ul-ru:un-Zubastikkkk"},"ts":1687465395590}
        if (!Objects.equals(jsonObject.getJSONObject("body").getString("message"), "tip_menu")) {
            BongaCamsDataBase dataBase = new BongaCamsDataBase(performerName);
            String username = jsonObject.getJSONObject("body").getJSONObject("author").getString("displayName");
            String userMessage = jsonObject.getJSONObject("body").getString("message");
            dataBase.addMessage(userMessage, username, timestamp);
            Logger.log("CHAT_INCOMING_MESSAGE: " + username + " :" + userMessage);
        }
    }

    private void start() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable bongaChecker = () -> {
            try {

                boolean isLive = new BongaReader(performerName).getResult().getChatShowStatusOptions().isOffline();
                if (currStatus == null) {
                    if (!isLive) {
                        connectToWebSocket();
                        Logger.log(performerName + " ist online.");
                    } else {
                        Logger.log(performerName + " ist offline.");
                    }
                    currStatus = isLive;
                }
                if (!Objects.equals(isLive, currStatus)) {
                    if (!isLive) {
                        connectToWebSocket();
                        Logger.log(performerName + " ist online.");
                    } else {
                        Logger.log(performerName + " ist offline.");
                    }
                    currStatus = isLive;
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        executor.scheduleAtFixedRate(bongaChecker, 0, 1, TimeUnit.SECONDS);


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
                    String joinRoomMessage;

                    joinRoomMessage = "{\"id\":1,\"name\":\"joinRoom\",\"args\":[\""
                            + chathost + "\",{\"username\":\"" +
                            username + "\",\"displayName\":\"" +
                            displayName + "\",\"location\":\"" +
                            location + "\",\"chathost\":\"" +
                            chathost + "\",\"isRu\":" +
                            isRu + ",\"isPerformer\":false,\"hasStream\":false,\"isLogged\":false,\"isPayable\":false,\"showType\":\"public\"},\"" +
                            dataKey + "\"]}";

                    send(joinRoomMessage);
                    currId = 1;

                    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                    Runnable bongaChecker = () -> {
                        try {
                            if (!currStatus) {
                                send("{\"id\":" + currId + ",\"name\":\"ping\"}");

                                Logger.log("--- send Ping current Id :" + currId + " ---");

                            }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    };
                    Runnable syncUserList = () -> {
                        try {
                            if (!currStatus) {
                                send("{\"id\":" + currId + ",\"name\":\"ChatModule.syncUserList\",\"args\":[\"public-chat\"]}");

                                Logger.log("--- Synchronisiere Userliste current Id :" + currId + " ---");

                            }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    };
                    executor.scheduleAtFixedRate(syncUserList, 0, 1, TimeUnit.MINUTES);
                    executor.scheduleAtFixedRate(bongaChecker, 0, 2, TimeUnit.MINUTES);
                }

                @Override
                public void onMessage(String message) {
                    // Handle incoming messages
                    JSONObject jsonObject = new JSONObject(message);
                    long timestamp = 0L;
                    try {
                        Logger.nameLog(message, performerName);
                    } catch (IOException e) {
                        Logger.log(e.getMessage());
                    }
                    if (jsonObject.has("id")) {

                        currId = jsonObject.getInt("id") + 1;
                    }
                    if (jsonObject.has("ts")) {

                        timestamp = jsonObject.getLong("ts") + 1;
                    }
                    if (jsonObject.has("result")) {
                        if (!Objects.equals(jsonObject.get("result").toString(), "null")) {
                            //{"id":3,"result":{"totalUsers":324,"totalGuests":2534,"userListLimit":-1},"error":null}
                            if (jsonObject.getJSONObject("result").has("totalUsers")) {
                                int totalUsers = jsonObject.getJSONObject("result").getInt("totalUsers");
                                int totalGuests = jsonObject.getJSONObject("result").getInt("totalGuests");
                                int userListLimit = jsonObject.getJSONObject("result").getInt("userListLimit");
                                Logger.log("Zuschauer: " + (totalUsers + totalGuests) + " davon Gäste: " + totalGuests + " und Benutzer: " + totalUsers);
                                new BongaCamsDataBase(performerName).addUserList(totalUsers + totalGuests, totalUsers, totalGuests, userListLimit);
                            }
//                            if (jsonObject.getJSONObject("result").has("history")) {
//                                //{"id":2,"result":{"type":"public-chat","history":[{"ts":1687623977742,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687623979623,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687623983635,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687623986890,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687623990154,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687623993360,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687623996996,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687624004180,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687624009866,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687624011688,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687624013519,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687624016022,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687624017911,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"ts":1687624020124,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"id":"123","type":"public-chat","message":"Милая покажи попу","author":{"isBestMember":false,"role":"member","isRaiseModelAvailable":false,"displayName":"Smi-Flow123","friendRequestSettings":"all","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":false,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"01/01/2022","accessLevel":"gold","avatarUrl":"/images/avatars/15/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":124426607,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":true,"isMuted":false,"username":"Smi-Flow123","hasStream":false,"isPayable":true},"own":false},{"ts":1687624033170,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}},{"id":"124","type":"public-chat","message":"Go baby","author":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true},"own":false},{"author":{"isBestMember":false,"role":"member","isRaiseModelAvailable":false,"displayName":"vhbjcdn","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":false,"showType":"public","isBanned":false,"hasProfile":true,"messageSenderSettings":"all","signupDate":"06/23/2023","accessLevel":"gold","avatarUrl":"/images/avatars/53/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":172045454,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":true,"isMuted":false,"username":"vhbjcdn","hasStream":false,"isPayable":true},"own":false,"id":"125","type":"public-chat","message":"🍓 П&quot;орNО ма&quot;лоле&quot;ток в t&quot;elege -   map868 👧","l":""},{"id":"126","type":"public-chat","message":"Привет","author":{"isBestMember":false,"role":"member","isRaiseModelAvailable":false,"displayName":"Tohinb","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":false,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"06/24/2023","accessLevel":"gold","avatarUrl":"/images/avatars/88/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":172084431,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":true,"isMuted":false,"username":"Tohinb","hasStream":false,"isPayable":true},"own":false},{"ts":1687624102636,"type":"ServerMessageEvent:INCOMING_TIP","body":{"a":2,"st":"","f":{"isBestMember":false,"role":"member","isRaiseModelAvailable":true,"displayName":"Sachkho","friendRequestSettings":"select","isPaymentsDisabled":false,"isLogged":true,"isRaiseModelReadyToUse":true,"isMobileLayout":true,"showType":"public","isBanned":false,"isMobile":true,"hasProfile":true,"messageSenderSettings":"all","signupDate":"09/22/2022","accessLevel":"sapphire","avatarUrl":"/images/avatars/70/avatars.jpg","privateChatInvitationSettings":"all","hasNote":false,"userId":157268039,"isPerformer":false,"isMcm":false,"chathost":"syka001","isRu":false,"isMuted":false,"username":"Sachkho","hasStream":false,"isPayable":true}}}],"userList":{"totalUsers":375,"totalGuests":2639,"userListLimit":-1}},"error":null}
//
//                            }
                            if (jsonObject.getJSONObject("result").has("tk")) {
                                //String unKingName = jsonObject.getJSONObject("result").getJSONObject("tk").getString("un");
                                String dnKingName = jsonObject.getJSONObject("result").getJSONObject("tk").getString("dn");
                                int amount = jsonObject.getJSONObject("result").getJSONObject("tk").getInt("amount");
                                Logger.log("Der aktuelle König ist :" + dnKingName + " mit " + amount + " Token");

                            }
//                            if (jsonObject.getJSONObject("result").has("status")) {
//
//                                BongaCamsDataBase dataBase = new BongaCamsDataBase(performerName);
//                                dataBase.addStatus(jsonObject.getJSONObject("result").getString("status"), timestamp);
//
//                                switch (jsonObject.getJSONObject("result").getString("status")) {
//                                    case "offline" -> Logger.log(performerName + " ist Offline");
//                                    case "fullprivate" -> Logger.log(performerName + " ist Offline");
//                                    case "public" -> {
//                                        if (jsonObject.getJSONObject("result").has("tipMenu")) {
//                                            Logger.log("--- Tip Menü ---");
//                                            Logger.log(jsonObject.getJSONObject("result").getString("tipMenu"));
//                                        }
//                                        Logger.log(performerName + " ist Öffentlich");
//                                        send("{\"id\":2,\"name\":\"ChatModule.connect\",\"args\":[\"public-chat\"]}");
//                                        Logger.log("Mit dem chat von '" + performerName + "' verbunden");
//                                        currId = jsonObject.getInt("id") + 1;
//                                    }
//                                    case "away" -> Logger.log(performerName + " ist away");
//                                    default ->
//                                            Logger.log("Status unbekannt: " + jsonObject.getJSONObject("result").getString("status"));
//                                }
//                            }
                        }
                    }

                    if (jsonObject.has("type")) {
                        String ServerMessageEvent = jsonObject.getString("type");
                        switch (ServerMessageEvent) {
                            case "ServerMessageEvent:DELETE_MESSAGE" -> {
                                //{"type":"ServerMessageEvent:DELETE_MESSAGE","body":{"username":"rthrth34egr","ddn":true},"ts":1687465658710}
                                Logger.log("DELETE_MESSAGE: Username = " + jsonObject.getJSONObject("body").getString("username"));
                            }
                            case "ServerMessageEvent:INCOMING_NOTICE" -> {
                                //{"type":"ServerMessageEvent:INCOMING_NOTICE","body":{"style":{"fontFamily":"Comic Sans MS","color":"#008000","fontSize":"120%"},"type":"notice","message":"{\"t\":\"cb:tip-king\",\"m\":\"@username, you are my King! Thank you for the @tipsum Tokens!\",\"d\":{\"un\":\"RomanLyubovny\",\"dn\":\"LyubovnyRoman\",\"type\":\"tkn2\",\"tipsum\":\"4473\"}}","lang":"enc:en"},"ts":1687467601846}
                                Logger.log("INCOMING_NOTICE");
                            }
                            case "ServerMessageEvent:INCOMING_TIP" -> incomingTip(jsonObject, timestamp);

                            case "ServerMessageEvent:PERFORMER_STATUS_CHANGE" -> {
                                //{"ts":1687615387314,"type":"ServerMessageEvent:PERFORMER_STATUS_CHANGE","body":"offline"}
                                BongaCamsDataBase dataBase = new BongaCamsDataBase(performerName);
                                dataBase.addStatus(jsonObject.getString("body"), timestamp);
                                switch (jsonObject.getString("body")) {
                                    case "offline" -> Logger.log(performerName + " ist offline.");
                                    case "fullprivate" -> Logger.log(performerName + " ist fullprivate.");
                                    case "away" -> Logger.log(performerName + " ist away.");
                                    case "public" -> Logger.log(performerName + " ist öffentlich.");
                                    case "group" -> Logger.log(performerName + " ist in einem Gruppenchat.");
//                                    default -> {
//                                        //test
//                                        Logger.log("Unbekannter PERFORMER_STATUS_CHANGE: " + jsonObject.getString("body"));
//                                    }
                                }
                            }
                            case "ServerMessageEvent:ROOM_CLOSE" -> {
                                //{"type":"ServerMessageEvent:ROOM_CLOSE","body":{"reason":null,"code":"PING_TIMEOUT","name":"ssweetass"},"ts":1687548523443}
                                Logger.log("Raum geschlossen: " + jsonObject.getJSONObject("body").getString("code"));
                            }
                            case "ServerMessageEvent:PERFORMER_AUDIO_STATE:ROOM_CLOSE" ->
                                    Logger.log("PERFORMER_AUDIO_STATE:ROOM_CLOSE");

                            case "ServerMessageEvent:CHAT_INCOMING_MESSAGE" -> {
                                chatIncomingMessage(jsonObject, timestamp);
                            }
                            case "ServerMessageEvent:TOPIC_CHANGE" -> {
                                //{"ts":1687614327788,"type":"ServerMessageEvent:TOPIC_CHANGE","body":{"topic":"Don&#39;t be afraid when you enter, don&#39;t cry when you leave =) 6560 tk  kisses to all","isRu":false}}
                                Logger.log("TOPIC_CHANGE");
                            }
                            case "ServerMessageEvent:CHAT_CLEAR_HISTORY" -> {
                                //{"type":"ServerMessageEvent:CHAT_CLEAR_HISTORY","body":"{\"t\":\"public-chat\"}","ts":1687600547801}
                                Logger.log("CHAT_CLEAR_HISTORY");
                            }
                            case "ServerMessageEvent:TIP_KING_CHANGE" -> {
                                //{"type":"ServerMessageEvent:TIP_KING_CHANGE","body":{"amount":0,"un":"","dn":"","enabled":false},"ts":1687464144119}
                                Logger.log("TIP_KING_CHANGE");
                            }
                            case "ServerMessageEvent:UPDATE_TRACK_LIST" -> Logger.log("UPDATE_TRACK_LIST");
                            case "ServerMessageEvent:TOGGLE_TRACK" -> {
                                //{"type":"ServerMessageEvent:TOGGLE_TRACK","body":true,"ts":1687545540270}
                                Logger.log("TOGGLE_TRACK");
                            }
                            case "ServerMessageEvent:CHANGE_CHAT_SETTING" -> Logger.log("CHANGE_CHAT_SETTING");
                            case "ServerMessageEvent:PERFORMER_AUDIO_STATE" -> Logger.log("PERFORMER_AUDIO_STATE");
                            case "ServerMessageEvent:GROUP_CHAT_INVITATION_CHANGE" -> {
                                //{"type":"ServerMessageEvent:GROUP_CHAT_INVITATION_CHANGE","body":"{\"users\":[{\"d\":\"justm33\",\"u\":\"justm33\"},{\"d\":\"Barni9999\",\"u\":\"Barni9999\"},{\"d\":\"kent212-1\",\"u\":\"kent212-1\"}]}","ts":1687585065991}
                                Logger.log("GROUP_CHAT_INVITATION_CHANGE");
                            }
                            case "ServerMessageEvent:SKIP_TRACK" -> {
                                //{"ts":1687545836285,"type":"ServerMessageEvent:SKIP_TRACK","body":"6495e62e7c4351ee98051001"}
                                Logger.log("SKIP_TRACK");
                            }
                            case "ServerMessageEvent:GROUP_SHOW_RESPONSE" -> {
                                //{"ts":1687585166071,"type":"ServerMessageEvent:GROUP_SHOW_RESPONSE","body":"accept"}
                                Logger.log("GROUP_SHOW_RESPONSE");
                            }
                            default -> System.out.println(ServerMessageEvent);
                        }
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    // WebSocket connection is closed
                    Logger.log("WebSocket connection closed.");
                    connectToWebSocket();

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

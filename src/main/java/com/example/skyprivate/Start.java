package com.example.skyprivate;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Start {
    private static final ArrayList<Integer> stripStartFollower = new ArrayList<>();
    private static final ArrayList<StripChatReader> currStripChatPerformer = new ArrayList<>();
    private static final ArrayList<LivejasminData> currLJPerformer = new ArrayList<>(), startLJPerformer = new ArrayList<>();
    private static final boolean downloadSnapshot = false;
    //updateFollowerStatus In Minuten
    private static final int updateFollowerStatus = 15;
    private static ArrayList<BongaReader> currBongaPerform = new ArrayList<>();
    private static double minuteCounter = 0.0;
    private static boolean firstStart = true;

    private static void writefile(String videoUrl) {

        try {
            // Erstellen Sie den Ordner für die Ausgabedatei
            String outputPath = "H:\\Video Projekte\\Dennis Hype\\skyprivate\\";

            URL url = new URL(videoUrl);
            Path uriPath = Paths.get(url.getPath());
            Path finalPath = Paths.get(outputPath, String.valueOf(uriPath.subpath(0, uriPath.getNameCount())));
            System.out.println(finalPath);
            File outputDir = new File(finalPath.toString());
            outputDir.getAbsolutePath();
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Erstellen Sie den Dateinamen basierend auf der URL
            String fileName = videoUrl.substring(videoUrl.lastIndexOf("/") + 1);
            File outputFile = new File(outputDir, fileName);

            // Öffnen Sie eine Verbindung zur URL und lesen Sie die Daten


            InputStream inputStream = url.openStream();
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Schließen Sie die Streams
            inputStream.close();
            outputStream.close();

            System.out.println("Die Datei wurde erfolgreich heruntergeladen und im Ordner \"" + outputPath + "\" gespeichert.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {


        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // Erstellen eines Runnable, das Ihre Funktion ausführt
        Runnable task = () -> {
            // Hier können Sie Ihre Funktion aufrufen
            try {
                updateFollowerList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        // Erstellen eines Runnable für Funktion 2
        ArrayList<String> ljPerformers = new ArrayList<>();

        ljPerformers.add("LilithAnge");

        for (String curName : ljPerformers) {
            LiveJasminReader liveJasminReader = new LiveJasminReader(curName);
            startLJPerformer.add(liveJasminReader.getPerformerInfo());
        }

        ArrayList<String> bongaPerformer = new ArrayList<>();
        bongaPerformer.add("mina1992");
        bongaPerformer.add("scoftyss");

        for (String curPerformer : bongaPerformer) {

            BongaReader bongaReader = new BongaReader(curPerformer);
            bongaReader.getHistory().setOnline(false);
            bongaReader.getResult().getChatShowStatusOptions().setOffline(true);
            bongaReader.getResult().getChatShowStatusOptions().setAvailable(false);
            bongaReader.getResult().getChatTopicOptions().setCurrentTopic("");
            currBongaPerform.add(bongaReader);
        }

        Runnable ljOnlineChecker = () -> {
            try {
                checkLJOnlineStatus(ljPerformers);

                for (BongaReader currBonga : currBongaPerform) {
                    BongaReader bongaReader = new BongaReader(currBonga.getHistory().getUsername());
                    bongaIsOnline(currBonga, bongaReader);
                    bongaCurrentTopic(currBonga, bongaReader);
                    bongaIsAvaiable(currBonga, bongaReader);
                    bongaIsVipShow(currBonga, bongaReader);
                    bongaIsOffline(currBonga, bongaReader);
                    bongaIsGroupChat(currBonga, bongaReader);
                    bongaIsPrivatChat(currBonga, bongaReader);
                    bongaIsFullPrivatChat(currBonga, bongaReader);
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };


        // Planen Sie das Runnable, um alle 10 Minuten ausgeführt zu werden
        executor.scheduleAtFixedRate(task, 0, updateFollowerStatus, TimeUnit.MINUTES);
        executor.scheduleAtFixedRate(ljOnlineChecker, 0, 30, TimeUnit.SECONDS);

        // Weitere Code-Ausführung...
        //https://b-hls-08.doppiocdn.com/hls/59707439/59707439_480p_868_gdf4Qx36VTbNwj4m_1683323192.mp4
        //https://cloudflare.videos.skyprivate.com/513720dd73b2bf0a28bbb6c8faa42f13/audio/131/seg_1.ts
        //https://cloudflare.videos.skyprivate.com/66aba461edf2b996a893ad873b513d77/audio/131/seg_1.ts?p=eyJ0eXBlIjoidHJhbnNtdXgiLCJ2aWRlb0lEIjoiNjZhYmE0NjFlZGYyYjk5NmE4OTNhZDg3M2I1MTNkNzciLCJvd25lcklEIjo1ODY1NTMsImNyZWF0b3JJRCI6IiIsInNlZ21lbnREdXJhdGlvblNlY3MiOjQuMDE1NTA0MTU3MjE4NDQsInVzZVZPRE9URkUiOnRydWUsImZyb21NZXp6YW5pbmUiOmZhbHNlLCJ0cmFjayI6IjJhZDBlYjEyZThlYWU1MTU0OTllM2UwNjdlZTc1ZDhiIiwicmVuZGl0aW9uIjoiMzk1ODQwNTQ1IiwibXV4aW5nIjoiNDQ2NDcwMTQyIn0&s=Q8OSGcK2V8KTPMOERMOIalHClhnClCojwqsaW3fCq8K_OMOqwqTDk3pecMKaYw
        String urlString = "https://cloudflare.videos.skyprivate.com/513720dd73b2bf0a28bbb6c8faa42f13/video/480/seg_1.ts";

//        for (int i = 1; i < 16; i++) {
//            writefile("https://cloudflare.videos.skyprivate.com/66aba461edf2b996a893ad873b513d77/audio/131/seg_" + i + ".ts");
//        }


        //Skyprivate
        ArrayList<String> curMode = new ArrayList<>();
        ArrayList<Long> lastSeen = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://profiles.skyprivate.com/models/1s5qw-scofty-s.html");
        urls.add("https://profiles.skyprivate.com/models/1u8gk-marcelin-e.html");
        for (String ignored : urls) {
            curMode.add("");
            lastSeen.add(0L);
        }

        //StripChat
        ArrayList<String> stripUrls = new ArrayList<>();
        ArrayList<StripChatReader.PerformerMode> curStripMode = new ArrayList<>();


        //Urls
        stripUrls.add("Alexsaeli");
        stripUrls.add("Sheila_7");
        stripUrls.add("judith_cute");

        for (String url : stripUrls) {
            curStripMode.add(StripChatReader.PerformerMode.UNKNOWN);
            stripStartFollower.add(0);
            StripChatReader stripChatReader = new StripChatReader(url);
            stripChatReader.getUserInfo().getUser().setLive(false);
            stripChatReader.getUserInfo().getUser().setOnline(false);
            currStripChatPerformer.add(stripChatReader);
        }

        while (true) {

            getStripMode(stripUrls, curStripMode);
            int i;

            ArrayList<SkyPrivateReader> users = new ArrayList<>();
            try {
                i = 0;
                for (String url : urls) {
                    users.add(new SkyPrivateReader(url));
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

                    if (Objects.equals(users.get(i).getOnlineStatus(), "OFFLINE")) {
                        if (users.get(i).getLastSeen() != 0L) {
                            if (lastSeen.get(i) < users.get(i).getLastSeen()) {
                                lastSeen.set(i, users.get(i).getLastSeen());
                            }
                        }
                    }

                    if (!curMode.get(i).equals(users.get(i).getOnlineStatus())) {
                        curMode.set(i, users.get(i).getOnlineStatus());

                        if (Objects.equals(users.get(i).getOnlineStatus(), "OFFLINE")) {
                            Logger.log(" 🔴 " + users.get(i).getUserName() + " ist " + users.get(i).getOnlineStatus());

                            // Speichern der Informationen
                            addToDB(users.get(i).getUserName(), users.get(i).getOnlineStatus(), now.format(formatter),
                                    String.valueOf(users.get(i).getPricePerMinute()), String.valueOf(0));
                        } else {
                            Logger.log(" 🟢 " + users.get(i).getUserName() + " ist " + users.get(i).getOnlineStatus() + "-> "
                                    + users.get(i).getUrl() + " (" + users.get(i).getPricePerMinute() + " pro Minute)[" +
                                    lastSeen.get(i) + " Minuten] $" + lastSeen.get(i) * users.get(i).getPricePerMinute());

                            //Speichern der Informationen
                            addToDB(users.get(i).getUserName(), users.get(i).getOnlineStatus(), now.format(formatter),
                                    String.valueOf(users.get(i).getPricePerMinute()), String.valueOf(lastSeen.get(i)));
                            lastSeen.set(i, 0L);
                        }
                    }

                    i = i + 1;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Thread.sleep(30000);
            if (minuteCounter == updateFollowerStatus) {
                minuteCounter = 0.0;
            } else {
                minuteCounter = minuteCounter + 0.5;
            }
        }
    }

    private static void bongaIsFullPrivatChat(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isFullPrivatChat() != bongaReader.getResult().getChatShowStatusOptions().isFullPrivatChat()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isFullPrivatChat()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist Exklusiven privaten Chat", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " Exklusiver privater Chat beendet", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setFullPrivatChat(bongaReader.getResult().getChatShowStatusOptions().isFullPrivatChat());
        }
    }

    private static void bongaIsPrivatChat(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isPrivatChat() != bongaReader.getResult().getChatShowStatusOptions().isPrivatChat()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isPrivatChat()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einem Privat Chat", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " Privat Chat beendet", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setPrivatChat(bongaReader.getResult().getChatShowStatusOptions().isPrivatChat());
        }
    }

    private static void bongaIsGroupChat(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isGroupPrivatChat() != bongaReader.getResult().getChatShowStatusOptions().isGroupPrivatChat()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isGroupPrivatChat()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einem Gruppen Chat", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist Gruppen Chat beendet", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setGroupPrivatChat(bongaReader.getResult().getChatShowStatusOptions().isGroupPrivatChat());
        }
    }

    private static void bongaIsOffline(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isOffline() != bongaReader.getResult().getChatShowStatusOptions().isOffline()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isOffline()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist offline", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist Online", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setOffline(bongaReader.getResult().getChatShowStatusOptions().isOffline());
        }
    }

    private static void bongaIsVipShow(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isVipShow() != bongaReader.getResult().getChatShowStatusOptions().isVipShow()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isVipShow()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einer VIP Show", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einer VIP Show", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setVipShow(bongaReader.getResult().getChatShowStatusOptions().isVipShow());
        }
    }

    private static void bongaIsAvaiable(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isAvailable() != bongaReader.getResult().getChatShowStatusOptions().isAvailable()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isAvailable()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist verfügbar", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist nicht verfügbar", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setAvailable(bongaReader.getResult().getChatShowStatusOptions().isAvailable());
        }
    }

    private static void bongaCurrentTopic(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (bongaReader.getResult().getChatTopicOptions().isAvailable()) {
            if (!Objects.equals(currBonga.getResult().getChatTopicOptions().getCurrentTopic(), bongaReader.getResult().getChatTopicOptions().getCurrentTopic())) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " Current Topic:" + bongaReader.getResult().getChatTopicOptions().getCurrentTopic(), bongaReader);
                currBonga.getResult().getChatTopicOptions().setCurrentTopic((bongaReader.getResult().getChatTopicOptions().getCurrentTopic()));
            }
        }
    }

    private static void bongaIsOnline(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getHistory().isOnline() != bongaReader.getHistory().isOnline()) {
            if (bongaReader.getHistory().isOnline()) {
                Logger.bongaLog("🟢 " + bongaReader.getHistory().getDisplayName() + " ist Live!", bongaReader);
            } else {
                Logger.bongaLog("🔴 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live!", bongaReader);
            }
            currBonga.getHistory().setOnline(bongaReader.getHistory().isOnline());
        }
    }

    private static void getStripMode(ArrayList<String> stripUrls, ArrayList<StripChatReader.PerformerMode> curStripMode) throws Exception {
        int i = 0;
        for (String url : stripUrls) {
            StripChatReader.PerformerMode stripPerformerMode;
            StripChatReader stripChatReader = new StripChatReader(url);
            stripPerformerMode = stripChatReader.getPerformerMode();

            if (downloadSnapshot) {
                if (currStripChatPerformer.get(i).getUserInfo().getUser().getSnapshotTimestamp() == 0 && stripChatReader.getUserInfo().getUser().isLive() && stripPerformerMode == StripChatReader.PerformerMode.PUBLIC) {
                    stripChatReader.downloadSnapshot();
                    Logger.log(" 🖼 " + stripChatReader.getUserInfo().getUser().getUsername() + " Snapshot heruntergeladen.");
                    currStripChatPerformer.get(i).getUserInfo().getUser().setSnapshotTimestamp(stripChatReader.getUserInfo().getUser().getSnapshotTimestamp());
                }
                if (!Objects.equals(stripChatReader.getUserInfo().getUser().getSnapshotTimestamp(), currStripChatPerformer.get(i).getUserInfo().getUser().getSnapshotTimestamp()) && stripChatReader.getUserInfo().getUser().isLive() && stripPerformerMode == StripChatReader.PerformerMode.PUBLIC) {
                    stripChatReader.downloadSnapshot();
                    Logger.log(" 🖼 " + stripChatReader.getUserInfo().getUser().getUsername() + " Snapshot heruntergeladen.");
                    currStripChatPerformer.get(i).getUserInfo().getUser().setSnapshotTimestamp(stripChatReader.getUserInfo().getUser().getSnapshotTimestamp());
                }
            }

            if (stripStartFollower.get(i) == 0) {
                stripStartFollower.set(i, stripChatReader.getUserInfo().getUser().getFavoritedCount());
            }

            if (currStripChatPerformer.get(i).getUserInfo().getUser().isOnline() != (stripChatReader.getUserInfo().getUser().isOnline())) {
                if (stripChatReader.getUserInfo().getUser().isOnline()) {
                    Logger.log(" 🟢 " + stripChatReader.getUserInfo().getUser().getUsername() + " ist online!");
                } else {
                    Logger.log(" 🔴 " + stripChatReader.getUserInfo().getUser().getUsername() + " ist offline!");
                }
                currStripChatPerformer.get(i).getUserInfo().getUser().setOnline(stripChatReader.getUserInfo().getUser().isOnline());
            }

            if (currStripChatPerformer.get(i).getUserInfo().getUser().isLive() != stripChatReader.getUserInfo().getUser().isLive()) {
                if (stripChatReader.getUserInfo().getUser().isLive()) {
                    Logger.log(" ❤ " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " ist Live ");
                } else {
                    Logger.log(" 💔 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " ist nicht Live");
                }
                currStripChatPerformer.get(i).getUserInfo().getUser().setLive(stripChatReader.getUserInfo().getUser().isLive());
            }
            if (stripChatReader.getUserInfo().getUser().isLive()) {

                if (currStripChatPerformer.get(i).getCamInfo().getGoal() != stripChatReader.getCamInfo().getGoal()) {

                    if (currStripChatPerformer.get(i).getCamInfo().getGoal() == null) {
                        currStripChatPerformer.get(i).getCamInfo().setGoal(stripChatReader.getCamInfo().getGoal());
                    }

                    if (stripChatReader.getCamInfo().getGoal() == null || stripChatReader.getCamInfo().getGoal().getLeft() == 0) {
                        Logger.log(" ✅ " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " Ziel erreicht: " +
                                currStripChatPerformer.get(i).getCamInfo().getGoal().getDescription() + " -> " + stripChatReader.getUrl());
                    } else if (currStripChatPerformer.get(i).getCamInfo().getGoal().getLeft() != stripChatReader.getCamInfo().getGoal().getLeft()) {

                        Logger.log(" 🎯 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " Aktuelles Ziel:" +
                                stripChatReader.getCamInfo().getGoal().getDescription() + " [" + stripChatReader.getCamInfo().getGoal().getLeft() +
                                " Token übrig] User gab " + (stripChatReader.getCamInfo().getGoal().getSpent() - currStripChatPerformer.get(i).getCamInfo().getGoal().getSpent()) +
                                " Tokens");
                    }
                    currStripChatPerformer.get(i).getCamInfo().setGoal(stripChatReader.getCamInfo().getGoal());
                }

                if (!curStripMode.get(i).toString().equals(stripPerformerMode.toString())) {
                    switch (stripPerformerMode) {
                        case IDLE ->
                                Logger.log(" 💬 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " wird bald online gehen...");
                        case PRIVATE ->
                                Logger.log(" 👁 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " hat Spaß in einer Privat-Show");
                        case EXCLUSIVE_PRIVATE ->
                                Logger.log(" 📸 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " hat gerade Spaß in einer Exklusiv-Privat-Show");
                        case GROUP_SHOW ->
                                Logger.log(" 👨🏿‍🤝‍👨 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " ist in einer Gruppenshow...");
                        case PUBLIC ->
                                Logger.log(" ‍📺 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " ist Öffentlich -> " + stripChatReader.getUrl());
                        case OFFLINE ->
                                Logger.log(" ⏹ " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " Hat den Stream beendet...");
                        case VIRTUAL_PRIVATE ->
                                Logger.log(" 👁 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " hat Spaß in einer Virtuellen-Show");
                        default ->
                                Logger.log(currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " Unbekannt");
                    }
                    curStripMode.set(i, stripPerformerMode);
                }
            }

            if (stripChatReader.getUserInfo().getCurrPosition() != currStripChatPerformer.get(i).getUserInfo().getCurrPosition()) {
                Logger.log(" 🏆 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " ist von Platz " +
                        currStripChatPerformer.get(i).getUserInfo().getCurrPosition() + " auf Platz " + stripChatReader.getUserInfo().getCurrPosition() +
                        " (" + (currStripChatPerformer.get(i).getUserInfo().getCurrPosition() - stripChatReader.getUserInfo().getCurrPosition()) + ")");

                currStripChatPerformer.get(i).getUserInfo().setCurrPosition(stripChatReader.getUserInfo().getCurrPosition());
            }
            if (stripChatReader.getUserInfo().getCurrPoints() != currStripChatPerformer.get(i).getUserInfo().getCurrPoints()) {
                Logger.log(" 🎲 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " Hat jetzt " +
                        stripChatReader.getUserInfo().getCurrPoints() + " Punkte( davor " + currStripChatPerformer.get(i).getUserInfo().getCurrPoints() + " Punkte");

                currStripChatPerformer.get(i).getUserInfo().setCurrPoints(stripChatReader.getUserInfo().getCurrPoints());
            }
            i = i + 1;
        }
    }

    private static void updateFollowerList() throws Exception {
        if (firstStart) {
            firstStart = false;
        } else {
            Logger.log("--- Update Follower Liste ---");

            for (StripChatReader chatReader : currStripChatPerformer) {
                StripChatReader stripChatReader = new StripChatReader(chatReader.getUserInfo().getUser().getUsername());
                if (chatReader.getUserInfo().getUser().getFavoritedCount() != stripChatReader.getUserInfo().getUser().getFavoritedCount()) {
                    if (chatReader.getUserInfo().getUser().getFavoritedCount() > stripChatReader.getUserInfo().getUser().getFavoritedCount()) {
                        Logger.log(" ➖ " + chatReader.getUserInfo().getUser().getUsername() + " hat " +
                                (chatReader.getUserInfo().getUser().getFavoritedCount() - stripChatReader.getUserInfo().getUser().getFavoritedCount()) +
                                " Follower verloren! Sie hat jetzt insgesamt " + stripChatReader.getUserInfo().getUser().getFavoritedCount() + " Follower. ("
                                + (stripChatReader.getUserInfo().getUser().getFavoritedCount() - chatReader.getUserInfo().getUser().getFavoritedCount()) + " neue Follower dazubekommen)");
                    } else {
                        Logger.log(" ➕ " + chatReader.getUserInfo().getUser().getUsername() + " hat " +
                                (stripChatReader.getUserInfo().getUser().getFavoritedCount() - chatReader.getUserInfo().getUser().getFavoritedCount()) +
                                " Follower dazu bekommen! Sie hat jetzt insgesamt " + stripChatReader.getUserInfo().getUser().getFavoritedCount() + " Follower. ("
                                + (stripChatReader.getUserInfo().getUser().getFavoritedCount() - chatReader.getUserInfo().getUser().getFavoritedCount()) + " neue Follower dazubekommen)");
                    }
                    chatReader.getUserInfo().getUser().setFavoritedCount(stripChatReader.getUserInfo().getUser().getFavoritedCount());
                }
            }

            Logger.log("--- Update Follower Liste beendet ---");
        }
    }

    private static void addToDB(String username, String status, String date, String pricePerMinute, String lastSeen) throws IOException {
        LocalDate heute = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filename = "C:\\skyprivate\\" + heute.format(formatter) + "_" + username + ".log";
        PrintWriter w = new PrintWriter(new FileWriter(filename, true));
        w.println(status + "|" + date + "|" + username + "|" + pricePerMinute + "|" + lastSeen);
        w.flush();
        w.close();
    }

    private static void checkLJOnlineStatus(ArrayList<String> performerNames) throws IOException {

        int i = 0;
        for (String performerName : performerNames) {

            if (currLJPerformer.size() == 0) {
                for (String curName : performerNames) {

                    LiveJasminReader liveJasminReader = new LiveJasminReader(curName);
                    currLJPerformer.add(liveJasminReader.getPerformerInfo());
                    readLJStatus(liveJasminReader);
                    Logger.log(" 🎲 " + liveJasminReader.getPerformerInfo().getDisplay_name() +
                            " ist Level " + liveJasminReader.getPerformerInfo().getAchievement().getLevel() +
                            " und hat " + liveJasminReader.getPerformerInfo().getAchievement().getPoints() + " Punkte.");

                    if (!Objects.equals(liveJasminReader.getPerformerInfo().getPublic_show_status(), "none")) {
                        Logger.log(liveJasminReader.getPerformerInfo().getPublic_show_status());
                    }

                    if (liveJasminReader.getPerformerInfo().getHas_vip_show() == 1) {
                        Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " hat eine VIP Show");
                    }

                    if (!(liveJasminReader.getPerformerInfo().getHas_event_show() == null)) {
                        Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " hat eine Event Show (" + liveJasminReader.getPerformerInfo().getHas_event_show() + ")");
                    }
                }
            } else {
                LiveJasminReader liveJasminReader = new LiveJasminReader(performerName);

                //Original Online Status
                if (!Objects.equals(currLJPerformer.get(i).getOriginalStatus(), liveJasminReader.getPerformerInfo().getOriginalStatus())) {
                    switch (liveJasminReader.getPerformerInfo().getOriginalStatus()) {
                        case 0 ->
                                Logger.log(" 🔴 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist Offline");
                        case 1 ->
                                Logger.log(" 📺 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist jetzt LIVE");
                        case 2 ->
                                Logger.log(" 👁 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist in einer Privatshow");
                        case 3 ->
                                Logger.log("VIP " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist in einer VIP show");
                        default ->
                                Logger.log(" ? " + liveJasminReader.getPerformerInfo().getDisplay_name() + " Original Status: " + liveJasminReader.getPerformerInfo().getOriginalStatus());
                    }

                    currLJPerformer.get(i).setOriginalStatus(liveJasminReader.getPerformerInfo().getOriginalStatus());
                }
                //Sum of online performers
                if (!Objects.equals(currLJPerformer.get(i).getSum_of_online_performers(), liveJasminReader.getPerformerInfo().getSum_of_online_performers())) {
                    currLJPerformer.get(i).setStatus(liveJasminReader.getPerformerInfo().getStatus());
                    Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + "Sum of Online Performers: " + liveJasminReader.getPerformerInfo().getSum_of_online_performers());
                }

                //Achievement
                if (!Objects.equals(currLJPerformer.get(i).getAchievement().getPoints(), liveJasminReader.getPerformerInfo().getAchievement().getPoints())) {

                    Logger.log(" 🎲 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist jetzt Level " + liveJasminReader.getPerformerInfo().getAchievement().getLevel() +
                            " und hat " + liveJasminReader.getPerformerInfo().getAchievement().getPoints() +
                            "(" + (liveJasminReader.getPerformerInfo().getAchievement().getPoints() - startLJPerformer.get(i).getAchievement().getPoints()) + ") Punkte.");
                    currLJPerformer.get(i).setAchievement(liveJasminReader.getPerformerInfo().getAchievement());
                }
                //Public Show Status
                if (!Objects.equals(currLJPerformer.get(i).getPublic_show_status(), liveJasminReader.getPerformerInfo().getPublic_show_status())) {
                    if (!Objects.equals(liveJasminReader.getPerformerInfo().getPublic_show_status(), "none")) {
                        Logger.log(liveJasminReader.getPerformerInfo().getPublic_show_status());
                    }

                    currLJPerformer.get(i).setPublic_show_status(liveJasminReader.getPerformerInfo().getPublic_show_status());
                }

                //VIP Show
                if (!Objects.equals(currLJPerformer.get(i).getHas_vip_show(), liveJasminReader.getPerformerInfo().getHas_vip_show())) {
                    switch (liveJasminReader.getPerformerInfo().getHas_vip_show()) {
                        case 0 ->
                                Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " VIP Show beendet.");
                        case 1 ->
                                Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " hat eine VIP Show gestartet.");
                        default ->
                                Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " unbekannter VIP Status: " + liveJasminReader.getPerformerInfo().getHas_vip_show());
                    }
                    currLJPerformer.get(i).setHas_vip_show(liveJasminReader.getPerformerInfo().getHas_vip_show());
                }

                //Event Show
                if (!Objects.equals(currLJPerformer.get(i).getHas_event_show(), liveJasminReader.getPerformerInfo().getHas_event_show())) {

                    Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " hat eine Event Show (" + liveJasminReader.getPerformerInfo().getHas_event_show() + ")");

                    currLJPerformer.get(i).setHas_event_show(liveJasminReader.getPerformerInfo().getHas_event_show());
                }
                //Hot Show
                if (!Objects.equals(currLJPerformer.get(i).getHas_hot_show(), liveJasminReader.getPerformerInfo().getHas_hot_show())) {
                    switch (liveJasminReader.getPerformerInfo().getHas_hot_show()) {
                        case 0 ->
                                Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + "Macht keine hot Show.");
                        case 1 ->
                                Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " macht eine Hot Show.");
                        default ->
                                Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " unbekannter getHas_hot_show: " + liveJasminReader.getPerformerInfo().getHas_hot_show());
                    }
                    Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " hat eine Hot Show (" + liveJasminReader.getPerformerInfo().getHas_event_show() + ")");

                    currLJPerformer.get(i).setHas_event_show(liveJasminReader.getPerformerInfo().getHas_event_show());
                }
            }
            i = i + 1;
        }
    }

    private static void readLJStatus(LiveJasminReader liveJasminReader) {
        switch (liveJasminReader.getPerformerInfo().getStatus()) {
            case 0 -> Logger.log(" 🔴 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist offline");
            case 1 -> Logger.log(" 🟢 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist online");
            case 2 ->
                    Logger.log(" 👁 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist in einem Privatchat");
            default ->
                    Logger.log(liveJasminReader.getPerformerInfo().getDisplay_name() + " Unbekannter getStatus: " + liveJasminReader.getPerformerInfo().getStatus());
        }
    }

}
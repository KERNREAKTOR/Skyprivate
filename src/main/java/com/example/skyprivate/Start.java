package com.example.skyprivate;

import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
import com.example.skyprivate.CheckStatus.BongaCams.CheckStatus.StatusBongaCams;
import com.example.skyprivate.CheckStatus.LiveJasmin.CheckStatus.StatusLiveJasmin;
import com.example.skyprivate.CheckStatus.LiveJasmin.LiveJasminReader;
import com.example.skyprivate.CheckStatus.SkyPrivate.CheckStatus.StatusSkyPrivate;
import com.example.skyprivate.CheckStatus.SkyPrivate.SkyPrivateReader;
import com.example.skyprivate.CheckStatus.StripChat.CheckStatus.StatusStripChat;
import com.example.skyprivate.CheckStatus.StripChat.StripChatReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Start {
    private static final ArrayList<StripChatReader> stripStartFollower = new ArrayList<>();
    private static final ArrayList<StripChatReader> currStripChatPerformer = new ArrayList<>();
    private static final ArrayList<LiveJasminReader> currLiveJasminPerformer = new ArrayList<>();
    private static final ArrayList<SkyPrivateReader> currSkyPerformer = new ArrayList<>();
    private static final boolean downloadSnapshot = false;
    private static final ArrayList<BongaReader> currBongaPerformer = new ArrayList<>();
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

        //#############
        //# BongaCams #
        //#############

        ArrayList<String> bongaPerformer = new ArrayList<>();
        bongaPerformer.add("scoftyss");
        bongaPerformer.add("deewhite");

        for (String curPerformer : bongaPerformer) {
            BongaReader bongaReader = new BongaReader(curPerformer);
            bongaReader.getHistory().setOnline(false);
            bongaReader.getResult().getChatShowStatusOptions().setOffline(true);
            bongaReader.getResult().getChatShowStatusOptions().setAvailable(false);
            bongaReader.getResult().getChatTopicOptions().setCurrentTopic("");
            currBongaPerformer.add(bongaReader);
        }
        Runnable bongaChecker = () -> {
            try {

                for (BongaReader currBonga : currBongaPerformer) {
                    BongaReader bongaReader = new BongaReader(currBonga.getHistory().getUsername());
//                    if (!Objects.equals(currBonga.getResult().getTipPopupOptions().toString(), bongaReader.getResult().getTipPopupOptions().toString())) {
//                        Logger.bongaLog(bongaReader.getResult().getTipPopupOptions().toString(), bongaReader);
//                        currBonga.getResult().setTipPopupOptions(bongaReader.getResult().getTipPopupOptions());
//                    }

                    StatusBongaCams.bongaCurrentTopic(currBonga, bongaReader);
                    StatusBongaCams.bongaIsAvailable(currBonga, bongaReader);
                    StatusBongaCams.bongaIsVipShow(currBonga, bongaReader);
                    StatusBongaCams.bongaIsOffline(currBonga, bongaReader);
                    StatusBongaCams.bongaIsGroupChat(currBonga, bongaReader);
                    StatusBongaCams.bongaIsPrivatChat(currBonga, bongaReader);
                    StatusBongaCams.bongaIsFullPrivatChat(currBonga, bongaReader);
                    StatusBongaCams.bongaIsOnline(currBonga, bongaReader);
                    StatusBongaCams.bongaCheckJSONHistory(currBonga, bongaReader);
                    StatusBongaCams.bongaCheckJSONResult(currBonga, bongaReader);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        //##############
        //# JasminLive #
        //##############

        ArrayList<String> ljPerformers = new ArrayList<>();
        ljPerformers.add("LilithAnge");

        for (String curName : ljPerformers) {
            LiveJasminReader liveJasminReader = new LiveJasminReader(curName);
            liveJasminReader.getPerformerInfo().getAchievement().setPoints(0);
            liveJasminReader.getPerformerInfo().setOriginalStatus(-1);
            liveJasminReader.getPerformerInfo().setHas_vip_show(0);
            currLiveJasminPerformer.add(liveJasminReader);
        }

        Runnable ljOnlineChecker = () -> {
            try {
                for (LiveJasminReader currPerformer : currLiveJasminPerformer) {
                    LiveJasminReader liveJasminReader = new LiveJasminReader(currPerformer.getPerformerInfo().getDisplay_name());
                    StatusLiveJasmin.liveJasminGetAchievement(currPerformer, liveJasminReader);
                    StatusLiveJasmin.liveJasminGetOriginalStatus(currPerformer, liveJasminReader);

                    StatusLiveJasmin.liveJasminGetHas_vip_show(currPerformer, liveJasminReader);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        //##############
        //# SkyPrivate #
        //##############

        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://profiles.skyprivate.com/models/1s5qw-scofty-s.html");
        urls.add("https://profiles.skyprivate.com/models/1u8gk-marcelin-e.html");

        for (String currUrl : urls) {
            SkyPrivateReader skyPrivateReader = new SkyPrivateReader(currUrl);
            skyPrivateReader.setOnlineStatus(" ");
            currSkyPerformer.add(skyPrivateReader);
        }

        Runnable skyPrivateChecker = () -> {
            try {
                for (SkyPrivateReader currPerformer : currSkyPerformer) {
                    SkyPrivateReader skyPrivateReader = new SkyPrivateReader(currPerformer.getUrl());
                    StatusSkyPrivate.skyPrivateOnlineStatus(currPerformer, skyPrivateReader);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        //#############
        //# StripChat #
        //#############

        Runnable StripChatUpdateFollowerList = () -> {
            // Hier können Sie Ihre Funktion aufrufen
            try {
                updateFollowerList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        ArrayList<String> stripUrls = new ArrayList<>();
        ArrayList<StripChatReader.PerformerMode> curStripMode = new ArrayList<>();

        //Urls
        stripUrls.add("Alexsaeli");
        stripUrls.add("Sheila_7");
        stripUrls.add("judith_cute");
        stripUrls.add("jasminesummer");

        for (String url : stripUrls) {
            curStripMode.add(StripChatReader.PerformerMode.UNKNOWN);

            StripChatReader stripChatReader = new StripChatReader(url);
            stripStartFollower.add(stripChatReader);
            stripChatReader.getUserInfo().getUser().setLive(false);
            stripChatReader.getUserInfo().getUser().setOnline(false);
            stripChatReader.setPerformerMode(StripChatReader.PerformerMode.OFFLINE);
            stripChatReader.getCamInfo().getGoal().setLeft(-1);
            currStripChatPerformer.add(stripChatReader);
        }

        Runnable stripChatChecker = () -> {
            // Hier können Sie Ihre Funktion aufrufen
            try {
                for (StripChatReader currPerformer : currStripChatPerformer) {
                    StripChatReader stripChatReader = new StripChatReader(currPerformer.getUserInfo().getUser().getUsername());
                    StatusStripChat.stripChatOnlineStatus(currPerformer, stripChatReader);
                    StatusStripChat.stripChatLiveStatus(currPerformer, stripChatReader);
                    StatusStripChat.stripChatPerformerMode(currPerformer, stripChatReader);
                    StatusStripChat.stripChatGoal(currPerformer,stripChatReader);
                }

                getStripMode(stripUrls, curStripMode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        executor.scheduleAtFixedRate(StripChatUpdateFollowerList, 0, 30, TimeUnit.MINUTES);
        executor.scheduleAtFixedRate(ljOnlineChecker, 0, 30, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(bongaChecker, 0, 15, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(skyPrivateChecker, 0, 30, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(stripChatChecker, 0, 30, TimeUnit.SECONDS);

        // Weitere Code-Ausführung...
        //https://b-hls-08.doppiocdn.com/hls/59707439/59707439_480p_868_gdf4Qx36VTbNwj4m_1683323192.mp4
        //https://cloudflare.videos.skyprivate.com/513720dd73b2bf0a28bbb6c8faa42f13/audio/131/seg_1.ts
        //https://cloudflare.videos.skyprivate.com/66aba461edf2b996a893ad873b513d77/audio/131/seg_1.ts?p=eyJ0eXBlIjoidHJhbnNtdXgiLCJ2aWRlb0lEIjoiNjZhYmE0NjFlZGYyYjk5NmE4OTNhZDg3M2I1MTNkNzciLCJvd25lcklEIjo1ODY1NTMsImNyZWF0b3JJRCI6IiIsInNlZ21lbnREdXJhdGlvblNlY3MiOjQuMDE1NTA0MTU3MjE4NDQsInVzZVZPRE9URkUiOnRydWUsImZyb21NZXp6YW5pbmUiOmZhbHNlLCJ0cmFjayI6IjJhZDBlYjEyZThlYWU1MTU0OTllM2UwNjdlZTc1ZDhiIiwicmVuZGl0aW9uIjoiMzk1ODQwNTQ1IiwibXV4aW5nIjoiNDQ2NDcwMTQyIn0&s=Q8OSGcK2V8KTPMOERMOIalHClhnClCojwqsaW3fCq8K_OMOqwqTDk3pecMKaYw
        String urlString = "https://cloudflare.videos.skyprivate.com/513720dd73b2bf0a28bbb6c8faa42f13/video/480/seg_1.ts";

//        for (int i = 1; i < 16; i++) {
//            writefile("https://cloudflare.videos.skyprivate.com/66aba461edf2b996a893ad873b513d77/audio/131/seg_" + i + ".ts");
//        }
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

            if (stripChatReader.getUserInfo().getCurrPosition() != currStripChatPerformer.get(i).getUserInfo().getCurrPosition()) {
                Logger.log(" 🏆 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " ist von Platz "
                        + currStripChatPerformer.get(i).getUserInfo().getCurrPosition() + " auf Platz " +
                        stripChatReader.getUserInfo().getCurrPosition() + " (" +
                        (currStripChatPerformer.get(i).getUserInfo().getCurrPosition() - stripChatReader.getUserInfo().getCurrPosition()) + ")");

                currStripChatPerformer.get(i).getUserInfo().setCurrPosition(stripChatReader.getUserInfo().getCurrPosition());
            }
            if (stripChatReader.getUserInfo().getCurrPoints() != currStripChatPerformer.get(i).getUserInfo().getCurrPoints()) {
                Logger.log(" 🎲 " + currStripChatPerformer.get(i).getUserInfo().getUser().getUsername() + " Hat jetzt " +
                        stripChatReader.getUserInfo().getCurrPoints() + " Punkte( davor " + currStripChatPerformer.get(i).getUserInfo().getCurrPoints() + " Punkte)");

                currStripChatPerformer.get(i).getUserInfo().setCurrPoints(stripChatReader.getUserInfo().getCurrPoints());
            }
            i = i + 1;
        }
    }

    private static void updateFollowerList() throws Exception {
        if (firstStart) {
            firstStart = false;
        } else {
            int i =0;

            for (StripChatReader chatReader : currStripChatPerformer) {
                StripChatReader stripChatReader = new StripChatReader(chatReader.getUserInfo().getUser().getUsername());
                if (chatReader.getUserInfo().getUser().getFavoritedCount() != stripChatReader.getUserInfo().getUser().getFavoritedCount()) {
                    if (chatReader.getUserInfo().getUser().getFavoritedCount() > stripChatReader.getUserInfo().getUser().getFavoritedCount()) {
                        Logger.log(" ➖ " + chatReader.getUserInfo().getUser().getUsername() + " hat " +
                                (chatReader.getUserInfo().getUser().getFavoritedCount() - stripChatReader.getUserInfo().getUser().getFavoritedCount()) +
                                " Follower verloren! Sie hat jetzt insgesamt " + stripChatReader.getUserInfo().getUser().getFavoritedCount() +
                                " Follower. (" + (stripChatReader.getUserInfo().getUser().getFavoritedCount() - stripStartFollower.get(i).getUserInfo().getUser().getFavoritedCount()) +
                                " neue Follower dazubekommen)");
                    } else {
                        Logger.log(" ➕ " + chatReader.getUserInfo().getUser().getUsername() + " hat " +
                                (stripChatReader.getUserInfo().getUser().getFavoritedCount() - chatReader.getUserInfo().getUser().getFavoritedCount()) +
                                " Follower dazu bekommen! Sie hat jetzt insgesamt " + stripChatReader.getUserInfo().getUser().getFavoritedCount() +
                                " Follower. (" + (stripChatReader.getUserInfo().getUser().getFavoritedCount() - stripStartFollower.get(i).getUserInfo().getUser().getFavoritedCount()) +
                                " neue Follower dazubekommen)");
                    }
                    chatReader.getUserInfo().getUser().setFavoritedCount(stripChatReader.getUserInfo().getUser().getFavoritedCount());
                }
                i=i+1;
            }
        }
    }

}
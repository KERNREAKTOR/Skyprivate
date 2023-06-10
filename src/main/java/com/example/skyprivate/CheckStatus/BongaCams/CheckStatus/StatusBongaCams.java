package com.example.skyprivate.CheckStatus.BongaCams.CheckStatus;

import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
import com.example.skyprivate.Logger;
import com.example.skyprivate.SoundPlayer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.Objects;

public class StatusBongaCams {
    private static String lastOnline;

    public static void bongaCurrentTopic(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (bongaReader.getResult().getChatTopicOptions().isAvailable()) {

            if (!Objects.equals(currBonga.getResult().getChatTopicOptions().getCurrentTopic(), bongaReader.getResult().getChatTopicOptions().getCurrentTopic())) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " Current Topic:" + bongaReader.getResult().getChatTopicOptions().getCurrentTopic(), bongaReader);
                currBonga.getResult().getChatTopicOptions().setCurrentTopic((bongaReader.getResult().getChatTopicOptions().getCurrentTopic()));
            }
        }
    }

    public static void bongaIsFullPrivatChat(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isFullPrivatChat() != bongaReader.getResult().getChatShowStatusOptions().isFullPrivatChat()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isFullPrivatChat()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist Exklusiven privaten Chat", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " Exklusiver privater Chat beendet", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setFullPrivatChat(bongaReader.getResult().getChatShowStatusOptions().isFullPrivatChat());
        }
    }

    public static void bongaIsPrivatChat(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isPrivatChat() != bongaReader.getResult().getChatShowStatusOptions().isPrivatChat()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isPrivatChat()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einem Privat Chat", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " hat den Privat Chat beendet", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setPrivatChat(bongaReader.getResult().getChatShowStatusOptions().isPrivatChat());
        }
    }

    public static void bongaIsGroupChat(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isGroupPrivatChat() != bongaReader.getResult().getChatShowStatusOptions().isGroupPrivatChat()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isGroupPrivatChat()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einem Gruppen Chat", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist Gruppen Chat beendet", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setGroupPrivatChat(bongaReader.getResult().getChatShowStatusOptions().isGroupPrivatChat());
        }
    }

    public static void bongaIsOffline(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isOffline() != bongaReader.getResult().getChatShowStatusOptions().isOffline()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isOffline()) {
                Logger.bongaLog("🔴 " + bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist offline", bongaReader);
            } else {
                Logger.bongaLog("🟢 " + bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist Online", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setOffline(bongaReader.getResult().getChatShowStatusOptions().isOffline());
        }
    }

    public static void bongaIsVipShow(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isVipShow() != bongaReader.getResult().getChatShowStatusOptions().isVipShow()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isVipShow()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einer VIP Show", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einer VIP Show", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setVipShow(bongaReader.getResult().getChatShowStatusOptions().isVipShow());
        }
    }

    public static void bongaIsAvailable(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isAvailable() != bongaReader.getResult().getChatShowStatusOptions().isAvailable()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isAvailable()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist verfügbar", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist nicht verfügbar", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setAvailable(bongaReader.getResult().getChatShowStatusOptions().isAvailable());
        }
    }

    public static void bongaCheckJSONHistory(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (!Objects.equals(currBonga.getJsonHistory(), bongaReader.getJsonHistory())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " History: " + bongaReader.getJsonHistory(), bongaReader);
        }
        currBonga.setJsonHistory(bongaReader.getJsonHistory());
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

            Logger.log("[StatusBongaCams.isFileAvailable]: " + e.getMessage());

            e.printStackTrace();
            return false;
        }
    }

    //    setJsonStreamOptions(bongaChatShowStatusOptions.getJSONObject("streamOptions").toString());
//    setJsonTipAfterPrivateOptions(bongaChatShowStatusOptions.getJSONObject("tipAfterPrivateOptions").toString());
    public static void bongaCheckJSONResult(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (!Objects.equals(currBonga.getJsonChatShowStatusOptions(), bongaReader.getJsonChatShowStatusOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatShowStatusOptions: " + bongaReader.getJsonChatShowStatusOptions(), bongaReader);
            currBonga.setJsonChatShowStatusOptions(bongaReader.getJsonChatShowStatusOptions());
        }
        if (!Objects.equals(currBonga.getJsonMiniProfileV2(), bongaReader.getJsonMiniProfileV2())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMiniProfileV2: " + bongaReader.getJsonMiniProfileV2(), bongaReader);
            currBonga.setJsonMiniProfileV2(bongaReader.getJsonMiniProfileV2());
        }
        if (!Objects.equals(currBonga.getJsonTipPopupOptions(), bongaReader.getJsonTipPopupOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonTipPopupOptions: " + bongaReader.getJsonTipPopupOptions(), bongaReader);
            currBonga.setJsonTipPopupOptions(bongaReader.getJsonTipPopupOptions());
        }
        if (!Objects.equals(currBonga.getJsonPrivatePopupOptions(), bongaReader.getJsonPrivatePopupOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonPrivatePopupOptions: " + bongaReader.getJsonPrivatePopupOptions(), bongaReader);
            currBonga.setJsonPrivatePopupOptions(bongaReader.getJsonPrivatePopupOptions());
        }
        if (!Objects.equals(currBonga.getJsonStylePanelOptions(), bongaReader.getJsonStylePanelOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonStylePanelOptions: " + bongaReader.getJsonStylePanelOptions(), bongaReader);
            currBonga.setJsonStylePanelOptions(bongaReader.getJsonStylePanelOptions());
        }
        if (!Objects.equals(currBonga.getJsonMiniProfileToggleOptions(), bongaReader.getJsonMiniProfileToggleOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMiniProfileToggleOptions: " + bongaReader.getJsonMiniProfileToggleOptions(), bongaReader);
            currBonga.setJsonMiniProfileToggleOptions(bongaReader.getJsonMiniProfileToggleOptions());
        }
        if (!Objects.equals(currBonga.getJsonMiniProfile(), bongaReader.getJsonMiniProfile())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMiniProfile: " + bongaReader.getJsonMiniProfile(), bongaReader);
            currBonga.setJsonMiniProfile(bongaReader.getJsonMiniProfile());
        }
        if (!Objects.equals(currBonga.getJsonChatHeaderOptions(), bongaReader.getJsonChatHeaderOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatHeaderOptions: " + bongaReader.getJsonChatHeaderOptions(), bongaReader);
            currBonga.setJsonChatHeaderOptions(bongaReader.getJsonChatHeaderOptions());
        }
        if (!Objects.equals(currBonga.getJsonChatBeforeOfflineNotificationOptions(), bongaReader.getJsonChatBeforeOfflineNotificationOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatBeforeOfflineNotificationOptions: " + bongaReader.getJsonChatBeforeOfflineNotificationOptions(), bongaReader);
            currBonga.setJsonChatBeforeOfflineNotificationOptions(bongaReader.getJsonChatBeforeOfflineNotificationOptions());
        }
//        if (!Objects.equals(currBonga.getJsonModelNavigationOptions(), bongaReader.getJsonModelNavigationOptions())) {
//            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonModelNavigationOptions: " + bongaReader.getJsonModelNavigationOptions(), bongaReader);
//            currBonga.setJsonModelNavigationOptions(bongaReader.getJsonModelNavigationOptions());
//        }
        if (!Objects.equals(currBonga.getJsonChatOptionsNew(), bongaReader.getJsonChatOptionsNew())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatOptionsNew: " + bongaReader.getJsonChatOptionsNew(), bongaReader);
            currBonga.setJsonChatOptionsNew(bongaReader.getJsonChatOptionsNew());
        }
        if (!Objects.equals(currBonga.getJsonMemberBalanceOptions(), bongaReader.getJsonMemberBalanceOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMemberBalanceOptions: " + bongaReader.getJsonMemberBalanceOptions(), bongaReader);
            currBonga.setJsonMemberBalanceOptions(bongaReader.getJsonMemberBalanceOptions());
        }
        if (!Objects.equals(currBonga.getJsonLoversButton(), bongaReader.getJsonLoversButton())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonLoversButton: " + bongaReader.getJsonLoversButton(), bongaReader);
            currBonga.setJsonLoversButton(bongaReader.getJsonLoversButton());
        }
        if (!Objects.equals(currBonga.getJsonMemberChatNotificationSettingsOptions(), bongaReader.getJsonMemberChatNotificationSettingsOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMemberChatNotificationSettingsOptions: " + bongaReader.getJsonMemberChatNotificationSettingsOptions(), bongaReader);
            currBonga.setJsonMemberChatNotificationSettingsOptions(bongaReader.getJsonMemberChatNotificationSettingsOptions());
        }
//        if (!Objects.equals(currBonga.getJsonChatTopicOptions(), bongaReader.getJsonChatTopicOptions())) {
//            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatTopicOptions: " + bongaReader.getJsonChatTopicOptions(), bongaReader);
//            currBonga.setJsonChatTopicOptions(bongaReader.getJsonChatTopicOptions());
//        }
        if (!Objects.equals(currBonga.getJsonStreamOptions(), bongaReader.getJsonStreamOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonStreamOptions: " + bongaReader.getJsonStreamOptions(), bongaReader);
            currBonga.setJsonStreamOptions(bongaReader.getJsonStreamOptions());
        }
        if (!Objects.equals(currBonga.getJsonTipAfterPrivateOptions(), bongaReader.getJsonTipAfterPrivateOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonTipAfterPrivateOptions: " + bongaReader.getJsonTipAfterPrivateOptions(), bongaReader);
            currBonga.setJsonTipAfterPrivateOptions(bongaReader.getJsonTipAfterPrivateOptions());
        }

    }

    private static void writeFile(String videoUrl) {
        if (isFileAvailable(videoUrl)) {
            try {
                // Erstellen Sie den Ordner für die Ausgabedatei
                String outputPath = "C:\\BongaCams\\";

                URL url = new URL(videoUrl);
                Path uriPath = Paths.get(url.getPath());
                Path finalPath = Paths.get(outputPath, String.valueOf(uriPath.subpath(0, uriPath.getNameCount())));

                File outputDir = new File(finalPath.toString());
                File Folder = new File(outputDir.getParent() + "\\" + StatusBongaCams.getLastOnline());

                if (!Folder.exists()) {
                    Folder.mkdirs();
                }
                File outputFile = new File(Folder + "\\" + finalPath.getFileName());
                // Öffnen Sie eine Verbindung zur URL und lesen Sie die Daten

                if (!outputFile.exists()) {
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

                    System.out.println(outputFile);
                }


            } catch (IOException e) {
                Logger.log("[StatusBongaCams.WriteFile] : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void DownloadViodeos(Deque<String> videoFiles) {


//        Deque<String> videoQueue = new ArrayDeque<>();
//        List<String> vUrls = new ArrayList<>(videoFiles);
//        videoFiles.clear();
//        vUrls.sort(Comparator.reverseOrder());

        //       System.out.println(videoFiles.size());
//        for(String curVideo : vUrls){
//            videoQueue.push(curVideo);
//        }
        while (!videoFiles.isEmpty()) {
            String curFile = videoFiles.pop();
            Thread thread = new Thread(() -> writeFile(curFile));
            thread.start();
        }
    }

    public static ArrayList<StreamInfo> getPlayList(String urlPlayList) throws IOException {

        URL url = new URL(urlPlayList);
        ArrayList<StreamInfo> streamInfos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("#EXT-X-STREAM-INF")) {
                    StreamInfo myInfo = new StreamInfo();
                    myInfo.setBandWith(Integer.valueOf(line.split(",")[0].split(":")[1].split("=")[1]));
                    myInfo.setResolution(line.split(",")[1].split("=")[1]);
                    myInfo.setWidth(Integer.valueOf(line.split(",")[1].split("=")[1].split("x")[0]));
                    myInfo.setHeight(Integer.valueOf(line.split(",")[1].split("=")[1].split("x")[1]));
                    myInfo.setCodecs(line.split(",")[2].split("=")[1]);
                    myInfo.setChunkLink(reader.readLine());
                    streamInfos.add(myInfo);
                }
            }
        } catch (Exception e) {
            Logger.log("[StatusBongaCams.getPlayList]: " + e.getMessage());
        }
        return streamInfos;
    }

    public static String GetChunks_m3u8(String performerName) throws Exception {
        BongaReader bongaReader = new BongaReader(performerName);
        //https://live-edge73.bcvcdn.com/hls/stream_Mashulya29/public-aac/stream_Mashulya29/chunks.m3u8
        String fileURL = bongaReader.getVideoUrl() + "/chunks.m3u8";
        StringBuilder content = new StringBuilder();
        URL url = new URL(fileURL);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        } catch (Exception e) {
            Logger.log("[StatusBongaCams.GetChunk]:" + e.getMessage());
        }
        return content.toString();
    }

    public static String GetUrlChunk(String fileURL) throws Exception {

        StringBuilder content = new StringBuilder();
        URL url = new URL(fileURL);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

        } catch (Exception e) {
            Logger.log("[StatusBongaCams.GetUrlChunk]:" + e.getMessage());
            e.printStackTrace();
        }
        return content.toString();
    }

    public static String getLastOnline() {
        return lastOnline;
    }

    public static void setLastOnline(String lastOnline) {
        StatusBongaCams.lastOnline = lastOnline;
    }

    public static void bongaIsOnline(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getHistory().isOnline() != bongaReader.getHistory().isOnline()) {
            if (bongaReader.getHistory().isOnline()) {
                String pvt = "";
                if (bongaReader.getResult().getPrivatePopupOptions().getAvailable()) {
                    if (bongaReader.getResult().getPrivatePopupOptions().getPrivate_chat().getAvailable()) {
                        pvt = pvt + "(Privater Chat " + bongaReader.getResult().getPrivatePopupOptions().getPrivate_chat().getPrivateChatTokensPerMinute() +
                                " Token die Minute) ";
                    }
                    if (bongaReader.getResult().getPrivatePopupOptions().getGroup_chat().getAvailable()) {
                        pvt = pvt + "(Gruppen Chat " + bongaReader.getResult().getPrivatePopupOptions().getGroup_chat().getPrivateChatTokensPerMinute() +
                                " Token die Minute) ";
                    }
                    if (bongaReader.getResult().getPrivatePopupOptions().getFull_private_chat().getAvailable()) {
                        pvt = pvt + "(Voll Privater Chat " + bongaReader.getResult().getPrivatePopupOptions().getFull_private_chat().getPrivateChatTokensPerMinute() +
                                " Token die Minute) ";
                    }
                    if (bongaReader.getResult().getPrivatePopupOptions().getVoyeur_chat().getAvailable()) {
                        pvt = pvt + "(Voyeur Chat " + bongaReader.getResult().getPrivatePopupOptions().getVoyeur_chat().getPrivateChatTokensPerMinute() +
                                " Token die Minute) ";
                    }
                }
                Logger.bongaLog("📺 " + bongaReader.getHistory().getDisplayName() + " ist Live! -> " + bongaReader.getPerformerURL() + " " + pvt, bongaReader);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
                setLastOnline(sdf.format(new Date()));
                SoundPlayer.playOnline();
            } else {
                Logger.bongaLog("💔 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live!", bongaReader);
            }
            currBonga.getHistory().setOnline(bongaReader.getHistory().isOnline());
        }
    }
}

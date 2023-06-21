package com.example.bongastream;

import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
import com.example.skyprivate.CheckStatus.BongaCams.CheckStatus.StatusBongaCams;
import com.example.skyprivate.CheckStatus.BongaCams.CheckStatus.StreamInfo;
import com.example.skyprivate.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BongaServer {
    //private static final String bongaPerformer = "lovewildguy";
    private static final String bongaPerformer = "scoftyss";
    private static String finalStreamUrl = null;

    private static List<String> readM3UPlaylist(String playlistContent, String videoUrl) {
        List<String> fileNames = new ArrayList<>();

        String[] lines = playlistContent.split("\n");
        for (String line : lines) {
            if (line.startsWith("l_")) {
                int commaIndex = line.indexOf('\r');
                if (commaIndex != -1) {
                    fileNames.add(videoUrl + "/" + line.replace("\r", ""));
                }
            }
        }

        return fileNames;
    }

    private static void getStreamLink(BongaReader bonga) throws Exception {
        if (finalStreamUrl == null) {
            //BongaReader bonga = new BongaReader(bongaPerformer);
            if (bonga.getHistory().isOnline()) {
                String streamUrl = bonga.getPlayList();
                String chunkList = null;
                String curRes = null;
                ArrayList<StreamInfo> streamInfo = StatusBongaCams.getPlayList(streamUrl);

                for (StreamInfo curStream : streamInfo) {
                    Logger.bongaLog("Auflösung: " + curStream.getResolution() +
                            " Bandbreite: " + curStream.getBandWith() + " Codecs: " + curStream.getCodecs(), bonga);
                    if (curStream.getBandWith() < 3600000 && curStream.getBandWith() != 0) {
                        chunkList = curStream.getChunkLink();
                        curRes = "Aktuelle Auflösung: " + curStream.getResolution() + " Bandbreite: " + curStream.getBandWith();
                    }
//                    if (Objects.equals(curStream.getResolution(), "1280x720")) {
//
//                        chunkList = curStream.getChunkLink();
//                        curRes = "Aktuelle Auflösung: " + curStream.getResolution() + " Bandbreite: " + curStream.getBandWith();
//                    }
                }

                Logger.bongaLog(curRes, bonga);
                System.out.println(curRes);
                assert chunkList != null;
                streamUrl = streamUrl.split("playlist.m3u8")[0] + chunkList.split("chunks.m3u8")[0];
                finalStreamUrl = streamUrl;
            }
        }
    }

    public static void main(String[] args) throws Exception {

        String curChuck = "";
        boolean curLive;

        //Deque<String> urlQuere = new ArrayDeque<>();
        //ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        BongaReader bongaReader = new BongaReader(bongaPerformer);
        curLive = bongaReader.getHistory().isOnline();

        if (curLive) {
            Logger.bongaLog("🟢 " + bongaReader.getHistory().getDisplayName() + " ist Live.", bongaReader);
        } else {
            Logger.bongaLog("🔴 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live.", bongaReader);
        }

        while (true) {
            try {

                bongaReader = new BongaReader(bongaPerformer);

                if (!Objects.equals(curLive, bongaReader.getHistory().isOnline())) {
                    if (curLive) {
                        Logger.bongaLog("🟢 " + bongaReader.getHistory().getDisplayName() + " ist Live.", bongaReader);
                    } else {
                        Logger.bongaLog("🔴 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live.", bongaReader);
                    }
                    curLive = bongaReader.getHistory().isOnline();
                }
                //System.out.println(StatusBongaCams.getPlayList(bongaReader.getPlayList()));
                if (bongaReader.getHistory().isOnline()) {

                    if (StatusBongaCams.getLastOnline() == null) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss_SSS");
                        StatusBongaCams.setLastOnline(sdf.format(new Date()));
                        getStreamLink(bongaReader);
                    }

                    String chuckChecker = StatusBongaCams.GetUrlChunk(finalStreamUrl + "chunks.m3u8");

                    if (!Objects.equals(curChuck, chuckChecker)) {
                        //Set<String> uniqueFileNames = new HashSet<>(readM3UPlaylist(chuckChecker, finalStreamUrl));
                        //List<String> sortedList = new ArrayList<>(uniqueFileNames);
                        //sortedList.sort(Comparator.naturalOrder());
                        // urlQuere.addAll(sortedList);

                        curChuck = chuckChecker;
                        //StatusBongaCams.DownloadVideos(urlQuere);
                        // DownloadManager downloadManager = new DownloadManager();


                        // Warten, bis alle Downloads abgeschlossen sind

                        //for (String fileLin: readM3UPlaylist(chuckChecker, finalStreamUrl)){
                        StatusBongaCams.writeFile(readM3UPlaylist(chuckChecker, finalStreamUrl).get(0));
                        //downloadManager.downloadFile(fileLin);
                        //}
                        //uniqueFileNames.clear();
//                        while (!urlQuere.isEmpty()) {
//                            String curFile = urlQuere.pop();
//                            downloadManager.downloadFile(curFile);
//                            //Thread thread = new Thread(() -> writeFile(curFile));
//                            //thread.start();
//                        }
                        //downloadManager.shutdown();
                    }

                } else {

                    StatusBongaCams.setLastOnline(null);
                }

            } catch (Exception e) {
                Logger.log("[BongaServer.checkChunk] :" + e.getMessage());
            }
        }
//        Runnable checkChunk = () -> {
//
//        };

//        Runnable BongaDownloadVideos = () -> {
//            try {
//                StatusBongaCams.DownloadVideos(urlQuere);
//
//            } catch (Exception e) {
//                Logger.log("[BongaServer.BongaDownloadVideos]: " + e.getMessage());
//            }
//        };
//        Runnable checkOnline = () -> {
//            try {
//                boolean isLive;
//                BongaReader bongaReader = new BongaReader(bongaPerformer);
//                isLive = bongaReader.getHistory().isOnline();
//                if (curLive[0] == null) {
//                    if (isLive) {
//                        Logger.bongaLog("🟢 " + bongaReader.getHistory().getDisplayName() + " ist Live.", bongaReader);
//
//                    } else {
//                        Logger.bongaLog("🔴 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live.", bongaReader);
//                    }
//                    curLive[0] = isLive;
//                }
//
//                if (isLive != curLive[0]) {
//                    if (isLive) {
//                        Logger.bongaLog("🟢 " + bongaReader.getHistory().getDisplayName() + " ist Live.", bongaReader);
//
//                    } else {
//                        Logger.bongaLog("🔴 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live.", bongaReader);
//                    }
//                    curLive[0] = isLive;
//                }
//            } catch (Exception e) {
//                Logger.log(e.getMessage());
//
//            }
//        };

        //executor.scheduleAtFixedRate(checkChunk, 0, 2, TimeUnit.SECONDS);
        //executor.scheduleAtFixedRate(BongaDownloadVideos, 0, 2, TimeUnit.SECONDS);
        //executor.scheduleAtFixedRate(checkOnline, 0, 2, TimeUnit.SECONDS);
        //executor.scheduleAtFixedRate(checkStream, 0, 5, TimeUnit.SECONDS);
    }
}

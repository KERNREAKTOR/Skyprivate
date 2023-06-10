package com.example.bongastream;

import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
import com.example.skyprivate.CheckStatus.BongaCams.CheckStatus.StatusBongaCams;
import com.example.skyprivate.CheckStatus.BongaCams.CheckStatus.StreamInfo;
import com.example.skyprivate.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BongaServer {
    private static final String bongaPerformer = "girl-pleasure";
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

    private static void getStreamLink() throws Exception {
        if (finalStreamUrl == null) {
            BongaReader bonga = new BongaReader(bongaPerformer);
            if (bonga.getHistory().isOnline()) {
                String streamUrl = bonga.getPlayList();
                String chunkList = null;
                String curRes = null;
                ArrayList<StreamInfo> streamInfo = StatusBongaCams.getPlayList(streamUrl);

                for (StreamInfo curStream : streamInfo) {
                    if (curStream.getBandWith() < 3600000) {

                        chunkList = curStream.getChunkLink();
                        curRes = "Auflösung: " + curStream.getResolution() + "\r\n" + "Bandbreite: " + curStream.getBandWith();

                    }
                }

                System.out.println(curRes);
                assert chunkList != null;
                streamUrl = streamUrl.split("playlist.m3u8")[0] + chunkList.split("chunks.m3u8")[0];
                finalStreamUrl = streamUrl;
            }
        }
    }

    public static void main(String[] args) {

        final Boolean[] curLive = {null};
        final String[] curChuck = {""};

        Deque<String> urlQuere = new ArrayDeque<>();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable checkChunk = () -> {
            try {

                BongaReader bongaReader = new BongaReader(bongaPerformer);
                //System.out.println(StatusBongaCams.getPlayList(bongaReader.getPlayList()));
                if (bongaReader.getHistory().isOnline()) {

                    if (StatusBongaCams.getLastOnline() == null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss_SSS");
                        StatusBongaCams.setLastOnline(sdf.format(new Date()));
                        getStreamLink();
                    }

                    String chuckChecker = StatusBongaCams.GetUrlChunk(finalStreamUrl + "chunks.m3u8");

                    if (!Objects.equals(curChuck[0], chuckChecker)) {
                        Set<String> uniqueFileNames = new HashSet<>(readM3UPlaylist(chuckChecker, finalStreamUrl));
                        List<String> sortedList = new ArrayList<>(uniqueFileNames);
                        sortedList.sort(Comparator.naturalOrder());
                        urlQuere.addAll(sortedList);
                        uniqueFileNames.clear();
                        curChuck[0] = chuckChecker;
                    }
                } else {
                    StatusBongaCams.setLastOnline(null);
                }

            } catch (Exception e) {
                Logger.log("[BongaServer.checkChunk] :" + e.getMessage());
            }
        };

        Runnable BongaDownloadVideos = () -> {
            try {
                StatusBongaCams.DownloadViodeos(urlQuere);

            } catch (Exception e) {
                Logger.log("[BongaServer.BongaDownloadVideos]: " + e.getMessage());
            }
        };
        Runnable checkOnline = () -> {
            try {
                boolean isLive;
                BongaReader bongaReader = new BongaReader(bongaPerformer);
                isLive = bongaReader.getHistory().isOnline();
                if (curLive[0] == null) {
                    if (isLive) {
                        Logger.bongaLog("🟢 " + bongaReader.getHistory().getDisplayName() + " ist Live.", bongaReader);

                    } else {
                        Logger.bongaLog("🔴 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live.", bongaReader);
                    }
                    curLive[0] = isLive;
                }

                if (isLive != curLive[0]) {
                    if (isLive) {
                        Logger.bongaLog("🟢 " + bongaReader.getHistory().getDisplayName() + " ist Live.", bongaReader);

                    } else {
                        Logger.bongaLog("🔴 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live.", bongaReader);
                    }
                    curLive[0] = isLive;
                }
            } catch (Exception e) {
                Logger.log(e.getMessage());

            }
        };

        executor.scheduleAtFixedRate(checkChunk, 0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(BongaDownloadVideos, 0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(checkOnline, 0, 1, TimeUnit.SECONDS);
        //executor.scheduleAtFixedRate(checkStream, 0, 5, TimeUnit.SECONDS);
    }
}

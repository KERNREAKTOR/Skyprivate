package com.example.bongastream;

import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
import com.example.skyprivate.CheckStatus.BongaCams.CheckStatus.StatusBongaCams;
import com.example.skyprivate.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BongaServer {
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

    public static void main(String[] args) {
        String bongaPerformer = "scoftyss";
        final Boolean[] curLive = {null};
        final String[] curChuck = {""};
        Deque<String> urlQuere = new ArrayDeque<>();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable checkChunk = () -> {
            try {

                BongaReader bongaReader = new BongaReader(bongaPerformer);
                if (bongaReader.getHistory().isOnline()) {

                    if (StatusBongaCams.getLastOnline() == null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss_SSS");
                        StatusBongaCams.setLastOnline(sdf.format(new Date()));
                        Logger.bongaLog(bongaReader.getHistory().getUsername() + " ist online!", bongaReader);
                    }

                    String chuckChecker = StatusBongaCams.GetChunks_m3u8(bongaPerformer);

                    if (!Objects.equals(curChuck[0], chuckChecker)) {
                        Set<String> uniqueFileNames = new HashSet<>(readM3UPlaylist(chuckChecker, bongaReader.getVideoUrl()));
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
                Logger.log(e.getMessage());
            }
        };

        Runnable BongaDownloadVideos = () -> {
            try {
                StatusBongaCams.DownloadViodeos(urlQuere);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        Runnable checkOnline = () -> {
            try {
                boolean isLive;
                BongaReader bongaReader = new BongaReader(bongaPerformer);
                isLive = bongaReader.getHistory().isOnline();
                if (curLive[0] == null) {
                    curLive[0] = isLive;
                }
                if(isLive != curLive[0]){
                    if (isLive){
                        Logger.bongaLog("🟢 " + bongaReader.getHistory().getUsername() + " ist Live.", bongaReader);
                    }else{
                        Logger.bongaLog("🔴 " +bongaReader.getHistory().getUsername() + " ist Offline.", bongaReader);
                    }
                    curLive[0] = isLive;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        executor.scheduleAtFixedRate(checkChunk, 0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(BongaDownloadVideos, 0, 2, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(checkOnline, 0, 10, TimeUnit.SECONDS);
    }
}

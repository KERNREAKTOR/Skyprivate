package com.example.bongastream;

import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
import com.example.skyprivate.CheckStatus.BongaCams.CheckStatus.StatusBongaCams;
import com.example.skyprivate.CheckStatus.BongaCams.CheckStatus.StreamInfo;
import com.example.skyprivate.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BongaServer {
    //private static final String bongaPerformer = "princessara";
    //private static final String bongaPerformer = "tenderpassion";
    //private static final String bongaPerformer = "ladysunshine-";
    //private static final String bongaPerformer = "scoftyss";
    private static final String bongaPerformer = "annaplayboy";
    private static String finalStreamUrl = null;

    public static void mergeVideos(String outputFile, String directoryPath) {
        String ffmpegCmd = "libs/ffmpeg";
        StringBuilder cmd = new StringBuilder();

        String fileExtension = ".ts"; // Gewünschte Dateierweiterung

        ArrayList<String> fileList = new ArrayList<>();

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(fileExtension)) {
                    fileList.add(file.toString());
                    //Logger.log(file.getName());
                }
            }
        }

        Collections.sort(fileList, new FilenameComparator());

        Logger.log("Starte die zusammenführung der " + fileList.size() + " Videos");
        // Konstruiere den FFmpeg-Befehl
        cmd.append(ffmpegCmd).append(" -i \"concat:");
        for (String inputFile : fileList) {
            cmd.append(inputFile).append("|");
        }
        cmd.deleteCharAt(cmd.length() - 1); // Entferne das letzte Trennzeichen |
        cmd.append("\" -c copy ").append(outputFile);

        try {
            //libs/ffmpeg -i "concat:E:\stream_scoftyss\20230625_00_36_03_983\l_18973_1000067_500.ts|E:\stream_scoftyss\20230625_00_36_03_983\l_18973_100067_50.ts" -c copy H:\20230625_00_36_03_983.ts
            Process process = Runtime.getRuntime().exec(cmd.toString());
            // Lesen und Anzeigen des stdout-Streams

//            String line;
//
//            int exitValue = process.waitFor();
//            if (exitValue == 0) {
//                System.out.println("Zusammenfügen der Videos abgeschlossen.");
//            } else {
//                System.out.println("Fehler beim Zusammenfügen der Videos. Exit-Code: " + exitValue);
//                // Lese die Fehlermeldung aus dem Error-Stream
//                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//                while ((line = errorReader.readLine()) != null) {
//                    System.out.println(line);
//                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        //mergeVideos("H:\\stream_LadySunshine_001.mp4", "C:\\BongaCams\\hls\\stream_LadySunshine-\\public-aac\\stream_LadySunshine-\\2023\\06\\25\\17.34.47.940\\001");


        String curChuck = "";
        boolean curLive;

        BongaReader bongaReader = new BongaReader(bongaPerformer);
        curLive = bongaReader.getHistory().isOnline();

        if (curLive) {
            Logger.bongaLog("🟢 " + bongaReader.getHistory().getDisplayName() + " ist Live.", bongaReader);
        } else {
            Logger.bongaLog("🔴 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live.", bongaReader);
        }

        while (true) {
            Integer errorCode = null;
            try {
                errorCode = 0;

                bongaReader = new BongaReader(bongaPerformer);
                errorCode = 1;
                if (!Objects.equals(curLive, bongaReader.getHistory().isOnline())) {

                    errorCode = 2;
                    if (bongaReader.getHistory().isOnline()) {
                        errorCode = 3;
                        Logger.bongaLog("🟢 " + bongaReader.getHistory().getDisplayName() + " ist Live.", bongaReader);
                        errorCode = 4;
                    } else {
                        errorCode = 5;
                        Logger.bongaLog("🔴 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live.", bongaReader);
                        errorCode = 6;
                    }
                    errorCode = 7;
                    curLive = bongaReader.getHistory().isOnline();
                    errorCode = 8;
                }
                errorCode = 9;
                if (bongaReader.getHistory().isOnline()) {
                    errorCode = 10;

                    if (StatusBongaCams.getLastOnline() == null) {
                        errorCode = 11;

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy\\MM\\dd\\HH.mm.ss.SSS");
                        errorCode = 12;
                        StatusBongaCams.setLastOnline(sdf.format(new Date()));
                        errorCode = 13;
                        getStreamLink(bongaReader);
                        errorCode = 14;
                    }


                    errorCode = 15;
                    String chuckChecker = StatusBongaCams.GetUrlChunk(finalStreamUrl + "chunks.m3u8");
                    errorCode = 16;


                    if (!Objects.equals(curChuck, chuckChecker)) {
                        errorCode = 17;
                        curChuck = chuckChecker;
                        errorCode = 18;
                        StatusBongaCams.writeFile(readM3UPlaylist(chuckChecker, finalStreamUrl).get(0));
                        errorCode = 19;
                    }
                    errorCode = 20;
                } else {
                    errorCode = 21;
                    StatusBongaCams.setLastOnline(null);
                    errorCode = 22;
                }

            } catch (Exception e) {
                Logger.log("[BongaServer.checkChunk] :" + " Error Code:" + errorCode + e.getMessage());
            }
        }
    }

    static class FilenameComparator implements Comparator<String> {
        @Override
        public int compare(String filename1, String filename2) {
            int startIndex1 = filename1.lastIndexOf('_') + 1;
            int endIndex1 = filename1.lastIndexOf('.');
            int number1 = Integer.parseInt(filename1.substring(startIndex1, endIndex1));

            int startIndex2 = filename2.lastIndexOf('_') + 1;
            int endIndex2 = filename2.lastIndexOf('.');
            int number2 = Integer.parseInt(filename2.substring(startIndex2, endIndex2));

            return Integer.compare(number1, number2);
        }
    }
}

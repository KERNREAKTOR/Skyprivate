package com.example;

import com.example.skyprivate.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MergeVideosFiles {
    public static void main(String[] args) throws Exception {
        mergeVideos("H:\\princessara\\01072023_03.mp4","E:\\princessara\\01072023\\03");
        mergeVideos("H:\\princessara\\01072023_04.mp4","E:\\princessara\\01072023\\04");
        mergeVideos("H:\\princessara\\01072023_05.mp4","E:\\princessara\\01072023\\05");
        mergeVideos("H:\\princessara\\01072023_06.mp4","E:\\princessara\\01072023\\06");
        mergeVideos("H:\\princessara\\01072023_07.mp4","E:\\princessara\\01072023\\07");
        mergeVideos("H:\\princessara\\01072023_08.mp4","E:\\princessara\\01072023\\08");

    }

    public static void mergeVideos(String outputFile, String directoryPath) throws IOException {
        String ffmpegCmd = "libs/ffmpeg";
        //StringBuilder cmd = new StringBuilder();

        String fileExtension = ".ts"; // Gewünschte Dateierweiterung

        ArrayList<String> fileList = new ArrayList<>();

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        String filename = "C:\\Merge_Videos";
        File outputDir = new File(filename);

        outputDir.getAbsolutePath();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }


        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(fileExtension)) {
                    fileList.add(file.toString());

                    //Logger.log(file.getName());
                }
            }
        }
        filename = filename + "\\merge.txt";
        PrintWriter w = new PrintWriter(new FileWriter(filename, false));
        Collections.sort(fileList, new MergeVideosFiles.FilenameComparator());

        for (String inputFile : fileList) {
            w.println("file '" + inputFile + "'");
        }

        w.flush();
        w.close();
        Logger.log("Starte die zusammenführung der " + fileList.size() + " Videos");
        // Konstruiere den FFmpeg-Befehl
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(ffmpegCmd, "-f", "concat", "-safe", "0", "-i",  filename , "-c", "copy",   outputFile );



//        cmd.append(ffmpegCmd).append(" -i \"concat:");
//        for (String inputFile : fileList) {
//            cmd.append(inputFile).append("|");
//        }
//        cmd.deleteCharAt(cmd.length() - 1); // Entferne das letzte Trennzeichen |
//        cmd.append("\" -c copy ").append(outputFile);

        try {

            Process process = builder.start();


//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                System.out.println("Videos wurden erfolgreich zusammengefügt.");
//            } else {
//                System.out.println("Fehler beim Zusammenfügen der Videos. Exit-Code: " + exitCode);
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
//            }
            //libs/ffmpeg -i "concat:E:\stream_scoftyss\20230625_00_36_03_983\l_18973_1000067_500.ts|E:\stream_scoftyss\20230625_00_36_03_983\l_18973_100067_50.ts" -c copy H:\20230625_00_36_03_983.ts
            //Process process = Runtime.getRuntime().exec(cmd.toString());
            // Lesen und Anzeigen des stdout-Streams

//            String line;
////
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            while ((line = errorReader.readLine()) != null) {
//                System.out.println(line);
//            }
//            int exitValue = process.waitFor();
//            if (exitValue == 0) {
//                System.out.println("Zusammenfügen der Videos abgeschlossen.");
//            } else {
//                System.out.println("Fehler beim Zusammenfügen der Videos. Exit-Code: " + exitValue);
//                // Lese die Fehlermeldung aus dem Error-Stream
////                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
////                while ((line = errorReader.readLine()) != null) {
////                    System.out.println(line);
////                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void mergeVideos( String directoryPath) throws IOException {
        String ffmpegCmd = "libs/ffmpeg";
        //StringBuilder cmd = new StringBuilder();

        String fileExtension = ".ts"; // Gewünschte Dateierweiterung

        ArrayList<String> fileList = new ArrayList<>();

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        String filename = "C:\\Merge_Videos";
        File outputDir = new File(filename);

        outputDir.getAbsolutePath();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }


        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(fileExtension)) {
                    fileList.add(file.toString());

                    //Logger.log(file.getName());
                }
            }
        }
        filename = filename + "\\merge.txt";
        PrintWriter w = new PrintWriter(new FileWriter(filename, false));
        Collections.sort(fileList, new MergeVideosFiles.FilenameComparator());

        for (String inputFile : fileList) {
            w.println("file '" + inputFile + "'");
        }

        w.flush();
        w.close();
        Logger.log("Starte die zusammenführung der " + fileList.size() + " Videos");
        // Konstruiere den FFmpeg-Befehl
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(ffmpegCmd, "-f", "concat", "-safe", "0", "-i",  filename , "-c", "copy",   directoryPath + ".mp4" );



//        cmd.append(ffmpegCmd).append(" -i \"concat:");
//        for (String inputFile : fileList) {
//            cmd.append(inputFile).append("|");
//        }
//        cmd.deleteCharAt(cmd.length() - 1); // Entferne das letzte Trennzeichen |
//        cmd.append("\" -c copy ").append(outputFile);

        try {

            Process process = builder.start();


//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                System.out.println("Videos wurden erfolgreich zusammengefügt.");
//            } else {
//                System.out.println("Fehler beim Zusammenfügen der Videos. Exit-Code: " + exitCode);
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
//            }
            //libs/ffmpeg -i "concat:E:\stream_scoftyss\20230625_00_36_03_983\l_18973_1000067_500.ts|E:\stream_scoftyss\20230625_00_36_03_983\l_18973_100067_50.ts" -c copy H:\20230625_00_36_03_983.ts
            //Process process = Runtime.getRuntime().exec(cmd.toString());
            // Lesen und Anzeigen des stdout-Streams

//            String line;
////
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            while ((line = errorReader.readLine()) != null) {
//                System.out.println(line);
//            }
//            int exitValue = process.waitFor();
//            if (exitValue == 0) {
//                System.out.println("Zusammenfügen der Videos abgeschlossen.");
//            } else {
//                System.out.println("Fehler beim Zusammenfügen der Videos. Exit-Code: " + exitValue);
//                // Lese die Fehlermeldung aus dem Error-Stream
////                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
////                while ((line = errorReader.readLine()) != null) {
////                    System.out.println(line);
////                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
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

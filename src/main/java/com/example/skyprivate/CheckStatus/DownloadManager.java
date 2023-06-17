package com.example.skyprivate.CheckStatus;

import com.example.skyprivate.CheckStatus.BongaCams.CheckStatus.StatusBongaCams;
import com.example.skyprivate.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.skyprivate.CheckStatus.StripChat.StripChatReader.isFileAvailable;

public class DownloadManager {
    private static final int MAX_CONCURRENT_DOWNLOADS = 3;
    private final ExecutorService executorService;

    public DownloadManager() {
        executorService = Executors.newFixedThreadPool(MAX_CONCURRENT_DOWNLOADS);
    }

    public void downloadFile(String fileUrl) {
        Runnable downloadTask = createDownloadTask(fileUrl);
        executorService.execute(downloadTask);
    }

    private Runnable createDownloadTask(String fileUrl) {
        return () -> {
            // Hier den eigentlichen Download-Code einfügen
            // Verwenden Sie fileUrl zum Herunterladen der Datei
            // Sie können auch den Fortschritt des Downloads aktualisieren

            // Beispiel:
           // if (isFileAvailable(fileUrl)) {

                try {
                    // Erstellen Sie den Ordner für die Ausgabedatei
                    String outputPath = "C:\\BongaCams\\";

                    URL url = new URL(fileUrl);
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
                        System.out.println("Downloading " + fileUrl);
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

                        //System.out.println(outputFile);
                        System.out.println("🟢 Download completed: " + fileUrl);
                    }else{
                        Logger.log("[StatusBongaCams.WriteFile] : Die Datei " + outputFile.getName() + " ist bereits vorhanden");
                    }


                } catch (IOException e) {
                    Logger.log("[StatusBongaCams.WriteFile] : " + e.getMessage());
                    e.printStackTrace();
                }
//            }else{
//                Logger.log("🔴 [StatusBongaCams.WriteFile] : Die Datei '" +
//                        fileUrl + "' ist nicht verfügbar");
//            }
        };
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public static void main(String[] args) {
        DownloadManager downloadManager = new DownloadManager();

        // Beispielaufrufe für Downloads
        downloadManager.downloadFile("https://example.com/file1");
        downloadManager.downloadFile("https://example.com/file2");
        downloadManager.downloadFile("https://example.com/file3");
        downloadManager.downloadFile("https://example.com/file4");

        // Warten, bis alle Downloads abgeschlossen sind
        downloadManager.shutdown();
    }
}


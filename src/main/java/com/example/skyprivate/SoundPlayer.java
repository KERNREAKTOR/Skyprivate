package com.example.skyprivate;

import javax.sound.sampled.*;
import java.util.Objects;


public class SoundPlayer {
    public static void playOnline(){
        String soundFile = "/com/SoundFile/zapsplat_multimedia_ui_chime_tone_simple_004_99764.wav";

        try {
            // Öffnen des Audio-Streams
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResourceAsStream(soundFile)));

            // Erstellen eines AudioFormats
            AudioFormat format = audioInputStream.getFormat();

            // Erstellen eines DataLine. Info-Objekts für das Wiedergabeziel
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            // Öffnen der Line für die Wiedergabe
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);

            // Starten der Wiedergabe
            line.start();

            // Lesen und Schreiben der Audiodaten in den Puffer
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            // Beenden der Wiedergabe
            line.drain();
            line.close();
            audioInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void playReached(){
        String soundFile = "/com/SoundFile/zapsplat_multimedia_ui_alert_synth_key_001_99758.wav";

        try {
            // Öffnen des Audio-Streams
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResourceAsStream(soundFile)));

            // Erstellen eines AudioFormats
            AudioFormat format = audioInputStream.getFormat();

            // Erstellen eines DataLine. Info-Objekts für das Wiedergabeziel
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            // Öffnen der Line für die Wiedergabe
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);

            // Starten der Wiedergabe
            line.start();

            // Lesen und Schreiben der Audiodaten in den Puffer
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            // Beenden der Wiedergabe
            line.drain();
            line.close();
            audioInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

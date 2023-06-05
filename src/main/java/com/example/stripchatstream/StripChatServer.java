package com.example.stripchatstream;

import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
import com.example.skyprivate.CheckStatus.StripChat.StripChatReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class StripChatServer {
    public static void main(String[] args) throws Exception {
        StripChatReader stripChatReader = new StripChatReader("taty-tits");
        System.out.println(stripChatReader.getUserInfo().getUser().getId());
        //https://edge-hls.doppiocdn.com/hls/21895898/master/21895898_auto.m3u8
        System.out.println(GetM3u8(stripChatReader.getUserInfo().getUser().getId()));


    }
    public static String GetM3u8(int performerId) throws Exception {

        //https://b-hls-24.doppiocdn.com/hls/102642232/102642232_720p.m3u8
        String fileURL = "https://b-hls-24.doppiocdn.com/hls/" + performerId + "/" + performerId + "_720p.m3u8";
        StringBuilder content = new StringBuilder();
        URL url = new URL(fileURL);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }
        return content.toString();
    }
}

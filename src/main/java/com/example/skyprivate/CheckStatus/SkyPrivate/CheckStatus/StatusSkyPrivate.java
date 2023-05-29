package com.example.skyprivate.CheckStatus.SkyPrivate.CheckStatus;

import com.example.skyprivate.Logger;
import com.example.skyprivate.CheckStatus.SkyPrivate.SkyPrivateReader;

import java.io.IOException;
import java.util.Objects;

public class StatusSkyPrivate {
    public static void skyPrivateOnlineStatus(SkyPrivateReader currSkyPrivate, SkyPrivateReader skyPrivateReader) throws IOException {

        if (!Objects.equals(currSkyPrivate.getOnlineStatus(), skyPrivateReader.getOnlineStatus())) {
            if (Objects.equals(skyPrivateReader.getOnlineStatus(), "OFFLINE")) {
                Logger.SkyLog("🔴 " + skyPrivateReader.getUserName() + " ist OFFLINE", skyPrivateReader);
            } else {
                Logger.SkyLog("🟢 " + skyPrivateReader.getUserName() + " ist " + skyPrivateReader.getOnlineStatus() +
                        " -> " + skyPrivateReader.getUrl() + "(" + skyPrivateReader.getPricePerMinute() + "$ pro Minute)", skyPrivateReader);
            }
            currSkyPrivate.setOnlineStatus(skyPrivateReader.getOnlineStatus());
        }
    }
}

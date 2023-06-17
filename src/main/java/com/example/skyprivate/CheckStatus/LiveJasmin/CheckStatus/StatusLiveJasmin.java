package com.example.skyprivate.CheckStatus.LiveJasmin.CheckStatus;

import com.example.skyprivate.CheckStatus.LiveJasmin.LiveJasminReader;
import com.example.skyprivate.Logger;
import com.example.skyprivate.SoundPlayer;

import java.io.IOException;
import java.util.Objects;

public class StatusLiveJasmin {
    public static void liveJasminGetAchievement(LiveJasminReader currLiveJasmin, LiveJasminReader liveJasminReader) throws IOException {

        if (!Objects.equals(currLiveJasmin.getPerformerInfo().getAchievement().getPoints(), liveJasminReader.getPerformerInfo().getAchievement().getPoints())) {
            if (currLiveJasmin.getPerformerInfo().getAchievement().getPoints() == 0) {
                Logger.jasminLog("🎲 " + liveJasminReader.getPerformerInfo().getDisplay_name() +
                        " ist Level " + liveJasminReader.getPerformerInfo().getAchievement().getLevel() +
                        " und hat " + liveJasminReader.getPerformerInfo().getAchievement().getPoints() +
                        " Punkte.", liveJasminReader);
            } else {
                Logger.jasminLog("🎲 " + liveJasminReader.getPerformerInfo().getDisplay_name() +
                        " ist Level " + liveJasminReader.getPerformerInfo().getAchievement().getLevel() +
                        " und hat " + liveJasminReader.getPerformerInfo().getAchievement().getPoints() +
                        " Punkte (" + (liveJasminReader.getPerformerInfo().getAchievement().getPoints() - currLiveJasmin.getPerformerInfo().getAchievement().getPoints()) +
                        ")", liveJasminReader);
            }
            currLiveJasmin.getPerformerInfo().setAchievement(liveJasminReader.getPerformerInfo().getAchievement());
        }

        if (!Objects.equals(currLiveJasmin.getPerformerInfo().getPublic_show_status(), liveJasminReader.getPerformerInfo().getPublic_show_status())) {
            Logger.jasminLog(liveJasminReader.getPerformerInfo().getPublic_show_status(), liveJasminReader);
            currLiveJasmin.getPerformerInfo().setPublic_show_status((liveJasminReader.getPerformerInfo().getPublic_show_status()));
        }
    }

    public static void liveJasminGetOriginalStatus(LiveJasminReader currLiveJasmin, LiveJasminReader liveJasminReader) throws IOException {

        if (!Objects.equals(currLiveJasmin.getPerformerInfo().getOriginalStatus(), liveJasminReader.getPerformerInfo().getOriginalStatus())) {
            switch (liveJasminReader.getPerformerInfo().getOriginalStatus()) {
                case 0 ->
                        Logger.jasminLog("🔴 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist Offline", liveJasminReader);
                case 1 -> {
                    Logger.jasminLog("📺 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist jetzt LIVE -> " +
                            liveJasminReader.getUrl(), liveJasminReader);
                    SoundPlayer.playOnline();
                }
                case 2 ->
                        Logger.jasminLog("👁 " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist in einer Privatshow -> " +
                                liveJasminReader.getUrl(), liveJasminReader);
                case 3 ->
                        Logger.jasminLog("VIP " + liveJasminReader.getPerformerInfo().getDisplay_name() + " ist in einer VIP show -> " +
                                liveJasminReader.getUrl(), liveJasminReader);
                default ->
                        Logger.jasminLog("? " + liveJasminReader.getPerformerInfo().getDisplay_name() + " Original Status: " + liveJasminReader.getPerformerInfo().getOriginalStatus(), liveJasminReader);
            }

            currLiveJasmin.getPerformerInfo().setOriginalStatus(liveJasminReader.getPerformerInfo().getOriginalStatus());
        }
    }

    public static void liveJasminGetHas_vip_show(LiveJasminReader currLiveJasmin, LiveJasminReader liveJasminReader) throws IOException {

        if (!Objects.equals(currLiveJasmin.getPerformerInfo().getHas_vip_show(), liveJasminReader.getPerformerInfo().getHas_vip_show())) {
            switch (liveJasminReader.getPerformerInfo().getHas_vip_show()) {
                case 0 ->
                        Logger.jasminLog(liveJasminReader.getPerformerInfo().getDisplay_name() + " VIP Show beendet.", liveJasminReader);
                case 1 ->
                        Logger.jasminLog(liveJasminReader.getPerformerInfo().getDisplay_name() + " hat eine VIP Show gestartet -> "
                                + liveJasminReader.getUrl(), liveJasminReader);
                default ->
                        Logger.jasminLog(liveJasminReader.getPerformerInfo().getDisplay_name() + " unbekannter VIP Status: " + liveJasminReader.getPerformerInfo().getHas_vip_show(), liveJasminReader);
            }
            currLiveJasmin.getPerformerInfo().setHas_vip_show(liveJasminReader.getPerformerInfo().getHas_vip_show());
        }
    }
}

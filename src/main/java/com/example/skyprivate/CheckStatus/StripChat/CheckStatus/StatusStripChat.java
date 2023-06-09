package com.example.skyprivate.CheckStatus.StripChat.CheckStatus;

import com.example.skyprivate.CheckStatus.StripChat.StripChatReader;
import com.example.skyprivate.Logger;
import com.example.skyprivate.SoundPlayer;

import java.io.IOException;
import java.util.Objects;

public class StatusStripChat {
    public static void stripChatOnlineStatus(StripChatReader currStripChat, StripChatReader stripChatReader) throws IOException {

        if (!Objects.equals(currStripChat.getUserInfo().getUser().isOnline(), stripChatReader.getUserInfo().getUser().isOnline())) {
            if (stripChatReader.getUserInfo().getUser().isOnline()) {
                Logger.stripLog("🟢 " + stripChatReader.getUserInfo().getUser().getUsername() + " ist Online", stripChatReader);
            } else {
                Logger.stripLog("🔴 " + stripChatReader.getUserInfo().getUser().getUsername() + " ist Offline", stripChatReader);
            }
            currStripChat.getUserInfo().getUser().setOnline(stripChatReader.getUserInfo().getUser().isOnline());
        }
    }

    public static void stripChatLiveStatus(StripChatReader currStripChat, StripChatReader stripChatReader) throws IOException {

        if (currStripChat.getUserInfo().getUser().isLive() != stripChatReader.getUserInfo().getUser().isLive()) {
            if (stripChatReader.getUserInfo().getUser().isLive()) {
                Logger.stripLog("❤ " + currStripChat.getUserInfo().getUser().getUsername() + " ist Live ", stripChatReader);
            } else {
                Logger.stripLog("💔 " + currStripChat.getUserInfo().getUser().getUsername() + " ist nicht Live", stripChatReader);
            }
            currStripChat.getUserInfo().getUser().setLive(stripChatReader.getUserInfo().getUser().isLive());
        }
    }

    public static void stripChatPerformerMode(StripChatReader currStripChat, StripChatReader stripChatReader) throws IOException {

        if (!currStripChat.getPerformerMode().equals(stripChatReader.getPerformerMode())) {
            switch (stripChatReader.getPerformerMode()) {

                case IDLE ->
                        Logger.stripLog("💬 " + stripChatReader.getUserInfo().getUser().getUsername() + " wird bald online gehen...", stripChatReader);
                case PRIVATE ->
                        Logger.stripLog("👁 " + stripChatReader.getUserInfo().getUser().getUsername() + " hat Spaß in einer Privat-Show", stripChatReader);
                case EXCLUSIVE_PRIVATE ->
                        Logger.stripLog("📸 " + stripChatReader.getUserInfo().getUser().getUsername() + " hat gerade Spaß in einer Exklusiv-Privat-Show", stripChatReader);
                case GROUP_SHOW ->
                        Logger.stripLog("👨🏿‍🤝‍👨 " + stripChatReader.getUserInfo().getUser().getUsername() + "ist in einer Gruppenshow...", stripChatReader);
                case PUBLIC ->
                        Logger.stripLog("‍📺 " + stripChatReader.getUserInfo().getUser().getUsername() + " ist Öffentlich -> " + stripChatReader.getUrl(), stripChatReader);
                case OFFLINE ->
                        Logger.stripLog("⏹ " + stripChatReader.getUserInfo().getUser().getUsername() + " Hat den Stream beendet...", stripChatReader);
                case VIRTUAL_PRIVATE ->
                        Logger.stripLog("👁 " + stripChatReader.getUserInfo().getUser().getUsername() + " hat Spaß in einer Virtuellen-Show", stripChatReader);
                default ->
                        Logger.stripLog("? " + stripChatReader.getUserInfo().getUser().getUsername() + " Unbekannter PerformerMode", stripChatReader);
            }
            currStripChat.setPerformerMode(stripChatReader.getPerformerMode());
        }
    }

    public static void stripChatGoal(StripChatReader currStripChat, StripChatReader stripChatReader) throws IOException {
        if (stripChatReader.getUserInfo().getUser().isLive()) {


            if (currStripChat.getCamInfo().getGoal().getLeft() != stripChatReader.getCamInfo().getGoal().getLeft()) {
                if (stripChatReader.getCamInfo().getGoal() == null || stripChatReader.getCamInfo().getGoal().getLeft() == 0 ||
                        (!Objects.equals(currStripChat.getCamInfo().getGoal().getDescription(), stripChatReader.getCamInfo().getGoal().getDescription()))) {

                    Logger.stripLog("✅ " + currStripChat.getUserInfo().getUser().getUsername() +
                            " Ziel erreicht: " + currStripChat.getCamInfo().getGoal().getDescription() + " -> " + stripChatReader.getUrl(), stripChatReader);
                    SoundPlayer.playReached();
                }
                Logger.stripLog("🎯 " + currStripChat.getUserInfo().getUser().getUsername() +
                        " Aktuelles Ziel:" + stripChatReader.getCamInfo().getGoal().getDescription() + " [" +
                        stripChatReader.getCamInfo().getGoal().getLeft() + " Token übrig] User gab " +
                        (stripChatReader.getCamInfo().getGoal().getSpent() - currStripChat.getCamInfo().getGoal().getSpent()) + " Tokens", stripChatReader);
                currStripChat.getCamInfo().setGoal(stripChatReader.getCamInfo().getGoal());
            }
        }
    }
}

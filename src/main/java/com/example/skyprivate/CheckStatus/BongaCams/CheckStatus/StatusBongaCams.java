package com.example.skyprivate.CheckStatus.BongaCams.CheckStatus;

import com.example.skyprivate.CheckStatus.BongaCams.BongaReader;
import com.example.skyprivate.Logger;

import java.io.IOException;
import java.util.Objects;

public class StatusBongaCams {
    public static void bongaCurrentTopic(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (bongaReader.getResult().getChatTopicOptions().isAvailable()) {

            if (!Objects.equals(currBonga.getResult().getChatTopicOptions().getCurrentTopic(), bongaReader.getResult().getChatTopicOptions().getCurrentTopic())) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " Current Topic:" + bongaReader.getResult().getChatTopicOptions().getCurrentTopic(), bongaReader);
                currBonga.getResult().getChatTopicOptions().setCurrentTopic((bongaReader.getResult().getChatTopicOptions().getCurrentTopic()));
            }
        }
    }

    public static void bongaIsFullPrivatChat(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isFullPrivatChat() != bongaReader.getResult().getChatShowStatusOptions().isFullPrivatChat()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isFullPrivatChat()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist Exklusiven privaten Chat", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " Exklusiver privater Chat beendet", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setFullPrivatChat(bongaReader.getResult().getChatShowStatusOptions().isFullPrivatChat());
        }
    }

    public static void bongaIsPrivatChat(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isPrivatChat() != bongaReader.getResult().getChatShowStatusOptions().isPrivatChat()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isPrivatChat()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einem Privat Chat", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " hat den Privat Chat beendet", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setPrivatChat(bongaReader.getResult().getChatShowStatusOptions().isPrivatChat());
        }
    }

    public static void bongaIsGroupChat(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isGroupPrivatChat() != bongaReader.getResult().getChatShowStatusOptions().isGroupPrivatChat()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isGroupPrivatChat()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einem Gruppen Chat", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist Gruppen Chat beendet", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setGroupPrivatChat(bongaReader.getResult().getChatShowStatusOptions().isGroupPrivatChat());
        }
    }

    public static void bongaIsOffline(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isOffline() != bongaReader.getResult().getChatShowStatusOptions().isOffline()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isOffline()) {
                Logger.bongaLog("🔴 " + bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist offline", bongaReader);
            } else {
                Logger.bongaLog("🟢 " + bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist Online", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setOffline(bongaReader.getResult().getChatShowStatusOptions().isOffline());
        }
    }

    public static void bongaIsVipShow(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isVipShow() != bongaReader.getResult().getChatShowStatusOptions().isVipShow()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isVipShow()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einer VIP Show", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist in einer VIP Show", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setVipShow(bongaReader.getResult().getChatShowStatusOptions().isVipShow());
        }
    }

    public static void bongaIsAvailable(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getResult().getChatShowStatusOptions().isAvailable() != bongaReader.getResult().getChatShowStatusOptions().isAvailable()) {
            if (bongaReader.getResult().getChatShowStatusOptions().isAvailable()) {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist verfügbar", bongaReader);
            } else {
                Logger.bongaLog(bongaReader.getResult().getChatShowStatusOptions().getDisplayName() + " ist nicht verfügbar", bongaReader);
            }
            currBonga.getResult().getChatShowStatusOptions().setAvailable(bongaReader.getResult().getChatShowStatusOptions().isAvailable());
        }
    }

    public static void bongaCheckJSONHistory(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (!Objects.equals(currBonga.getJsonHistory(), bongaReader.getJsonHistory())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " History: " + bongaReader.getJsonHistory(), bongaReader);
        }
        currBonga.setJsonHistory(bongaReader.getJsonHistory());
    }
//    setJsonStreamOptions(bongaChatShowStatusOptions.getJSONObject("streamOptions").toString());
//    setJsonTipAfterPrivateOptions(bongaChatShowStatusOptions.getJSONObject("tipAfterPrivateOptions").toString());
    public static void bongaCheckJSONResult(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (!Objects.equals(currBonga.getJsonChatShowStatusOptions(), bongaReader.getJsonChatShowStatusOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatShowStatusOptions: " + bongaReader.getJsonChatShowStatusOptions(), bongaReader);
            currBonga.setJsonChatShowStatusOptions(bongaReader.getJsonChatShowStatusOptions());
        }
        if (!Objects.equals(currBonga.getJsonMiniProfileV2(), bongaReader.getJsonMiniProfileV2())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMiniProfileV2: " + bongaReader.getJsonMiniProfileV2(), bongaReader);
            currBonga.setJsonMiniProfileV2(bongaReader.getJsonMiniProfileV2());
        }
        if (!Objects.equals(currBonga.getJsonTipPopupOptions(), bongaReader.getJsonTipPopupOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonTipPopupOptions: " + bongaReader.getJsonTipPopupOptions(), bongaReader);
            currBonga.setJsonTipPopupOptions(bongaReader.getJsonTipPopupOptions());
        }
        if (!Objects.equals(currBonga.getJsonPrivatePopupOptions(), bongaReader.getJsonPrivatePopupOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonPrivatePopupOptions: " + bongaReader.getJsonPrivatePopupOptions(), bongaReader);
            currBonga.setJsonPrivatePopupOptions(bongaReader.getJsonPrivatePopupOptions());
        }
        if (!Objects.equals(currBonga.getJsonStylePanelOptions(), bongaReader.getJsonStylePanelOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonStylePanelOptions: " + bongaReader.getJsonStylePanelOptions(), bongaReader);
            currBonga.setJsonStylePanelOptions(bongaReader.getJsonStylePanelOptions());
        }
        if (!Objects.equals(currBonga.getJsonMiniProfileToggleOptions(), bongaReader.getJsonMiniProfileToggleOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMiniProfileToggleOptions: " + bongaReader.getJsonMiniProfileToggleOptions(), bongaReader);
            currBonga.setJsonMiniProfileToggleOptions(bongaReader.getJsonMiniProfileToggleOptions());
        }
        if (!Objects.equals(currBonga.getJsonMiniProfile(), bongaReader.getJsonMiniProfile())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMiniProfile: " + bongaReader.getJsonMiniProfile(), bongaReader);
            currBonga.setJsonMiniProfile(bongaReader.getJsonMiniProfile());
        }
        if (!Objects.equals(currBonga.getJsonChatHeaderOptions(), bongaReader.getJsonChatHeaderOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatHeaderOptions: " + bongaReader.getJsonChatHeaderOptions(), bongaReader);
            currBonga.setJsonChatHeaderOptions(bongaReader.getJsonChatHeaderOptions());
        }
        if (!Objects.equals(currBonga.getJsonChatBeforeOfflineNotificationOptions(), bongaReader.getJsonChatBeforeOfflineNotificationOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatBeforeOfflineNotificationOptions: " + bongaReader.getJsonChatBeforeOfflineNotificationOptions(), bongaReader);
            currBonga.setJsonChatBeforeOfflineNotificationOptions(bongaReader.getJsonChatBeforeOfflineNotificationOptions());
        }
//        if (!Objects.equals(currBonga.getJsonModelNavigationOptions(), bongaReader.getJsonModelNavigationOptions())) {
//            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonModelNavigationOptions: " + bongaReader.getJsonModelNavigationOptions(), bongaReader);
//            currBonga.setJsonModelNavigationOptions(bongaReader.getJsonModelNavigationOptions());
//        }
        if (!Objects.equals(currBonga.getJsonChatOptionsNew(), bongaReader.getJsonChatOptionsNew())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatOptionsNew: " + bongaReader.getJsonChatOptionsNew(), bongaReader);
            currBonga.setJsonChatOptionsNew(bongaReader.getJsonChatOptionsNew());
        }
        if (!Objects.equals(currBonga.getJsonMemberBalanceOptions(), bongaReader.getJsonMemberBalanceOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMemberBalanceOptions: " + bongaReader.getJsonMemberBalanceOptions(), bongaReader);
            currBonga.setJsonMemberBalanceOptions(bongaReader.getJsonMemberBalanceOptions());
        }
        if (!Objects.equals(currBonga.getJsonLoversButton(), bongaReader.getJsonLoversButton())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonLoversButton: " + bongaReader.getJsonLoversButton(), bongaReader);
            currBonga.setJsonLoversButton(bongaReader.getJsonLoversButton());
        }
        if (!Objects.equals(currBonga.getJsonMemberChatNotificationSettingsOptions(), bongaReader.getJsonMemberChatNotificationSettingsOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonMemberChatNotificationSettingsOptions: " + bongaReader.getJsonMemberChatNotificationSettingsOptions(), bongaReader);
            currBonga.setJsonMemberChatNotificationSettingsOptions(bongaReader.getJsonMemberChatNotificationSettingsOptions());
        }
//        if (!Objects.equals(currBonga.getJsonChatTopicOptions(), bongaReader.getJsonChatTopicOptions())) {
//            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonChatTopicOptions: " + bongaReader.getJsonChatTopicOptions(), bongaReader);
//            currBonga.setJsonChatTopicOptions(bongaReader.getJsonChatTopicOptions());
//        }
        if (!Objects.equals(currBonga.getJsonStreamOptions(), bongaReader.getJsonStreamOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonStreamOptions: " + bongaReader.getJsonStreamOptions(), bongaReader);
            currBonga.setJsonStreamOptions(bongaReader.getJsonStreamOptions());
        }
        if (!Objects.equals(currBonga.getJsonTipAfterPrivateOptions(), bongaReader.getJsonTipAfterPrivateOptions())) {
            Logger.bongaLog(bongaReader.getHistory().getUsername() + " JsonTipAfterPrivateOptions: " + bongaReader.getJsonTipAfterPrivateOptions(), bongaReader);
            currBonga.setJsonTipAfterPrivateOptions(bongaReader.getJsonTipAfterPrivateOptions());
        }

    }

    public static void bongaIsOnline(BongaReader currBonga, BongaReader bongaReader) throws IOException {
        if (currBonga.getHistory().isOnline() != bongaReader.getHistory().isOnline()) {
            if (bongaReader.getHistory().isOnline()) {
                String pvt = "";
                if (bongaReader.getResult().getPrivatePopupOptions().getAvailable()) {
                    if (bongaReader.getResult().getPrivatePopupOptions().getPrivate_chat().getAvailable()) {
                        pvt = pvt + "(Privater Chat " + bongaReader.getResult().getPrivatePopupOptions().getPrivate_chat().getPrivateChatTokensPerMinute() +
                                " Token die Minute) ";
                    }
                    if (bongaReader.getResult().getPrivatePopupOptions().getGroup_chat().getAvailable()) {
                        pvt = pvt + "(Gruppen Chat " + bongaReader.getResult().getPrivatePopupOptions().getGroup_chat().getPrivateChatTokensPerMinute() +
                                " Token die Minute) ";
                    }
                    if (bongaReader.getResult().getPrivatePopupOptions().getFull_private_chat().getAvailable()) {
                        pvt = pvt + "(Voll Privater Chat " + bongaReader.getResult().getPrivatePopupOptions().getFull_private_chat().getPrivateChatTokensPerMinute() +
                                " Token die Minute) ";
                    }
                    if (bongaReader.getResult().getPrivatePopupOptions().getVoyeur_chat().getAvailable()) {
                        pvt = pvt + "(Voyeur Chat " + bongaReader.getResult().getPrivatePopupOptions().getVoyeur_chat().getPrivateChatTokensPerMinute() +
                                " Token die Minute) ";
                    }
                }
                Logger.bongaLog("📺 " + bongaReader.getHistory().getDisplayName() + " ist Live! -> " + bongaReader.getPerformerURL() + " " + pvt, bongaReader);
            } else {
                Logger.bongaLog("💔 " + bongaReader.getHistory().getDisplayName() + " ist nicht Live!", bongaReader);
            }
            currBonga.getHistory().setOnline(bongaReader.getHistory().isOnline());
        }
    }
}

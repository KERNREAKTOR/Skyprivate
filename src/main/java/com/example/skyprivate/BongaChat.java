package com.example.skyprivate;

class ChatTopicOptions{
    private boolean isAvailable;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getCurrentTopic() {
        return currentTopic;
    }

    public void setCurrentTopic(String currentTopic) {
        this.currentTopic = currentTopic;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    private String currentTopic,displayName;
}
class ShowStatus{
    public boolean isGroupPrivatChat() {
        return isGroupPrivatChat;
    }

    public void setGroupPrivatChat(boolean groupPrivatChat) {
        isGroupPrivatChat = groupPrivatChat;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isRtAvailable() {
        return rtAvailable;
    }

    public void setRtAvailable(boolean rtAvailable) {
        this.rtAvailable = rtAvailable;
    }

    public boolean isQoQWinner() {
        return isQoQWinner;
    }

    public void setQoQWinner(boolean qoQWinner) {
        isQoQWinner = qoQWinner;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

    public boolean isFullPrivatChat() {
        return isFullPrivatChat;
    }

    public void setFullPrivatChat(boolean fullPrivatChat) {
        isFullPrivatChat = fullPrivatChat;
    }

    public boolean isPrivatChat() {
        return isPrivatChat;
    }

    public void setPrivatChat(boolean privatChat) {
        isPrivatChat = privatChat;
    }

    public boolean isVipShow() {
        return isVipShow;
    }

    public void setVipShow(boolean vipShow) {
        isVipShow = vipShow;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    private boolean isGroupPrivatChat, isAvailable, rtAvailable, isQoQWinner, isOffline, isFullPrivatChat, isPrivatChat,
            isVipShow;
    private String displayName;
}
public class BongaChat {
    public ShowStatus getChatShowStatusOptions() {
        return chatShowStatusOptions;
    }

    public void setChatShowStatusOptions(ShowStatus chatShowStatusOptions) {
        this.chatShowStatusOptions = chatShowStatusOptions;
    }

    private ShowStatus chatShowStatusOptions;

    public ChatTopicOptions getChatTopicOptions() {
        return chatTopicOptions;
    }

    public void setChatTopicOptions(ChatTopicOptions chatTopicOptions) {
        this.chatTopicOptions = chatTopicOptions;
    }

    private ChatTopicOptions chatTopicOptions;
}

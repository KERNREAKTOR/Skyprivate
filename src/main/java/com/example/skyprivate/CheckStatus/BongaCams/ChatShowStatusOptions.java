package com.example.skyprivate.CheckStatus.BongaCams;

public class ChatShowStatusOptions {
    private boolean isGroupPrivatChat, isAvailable, rtAvailable, isQoQWinner, isOffline, isFullPrivatChat, isPrivatChat,
            isVipShow;
    private String displayName;

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

    @Override
    public String toString() {
        return "ChatShowStatusOptions{" +
                "isGroupPrivatChat=" + isGroupPrivatChat +
                ", isAvailable=" + isAvailable +
                ", rtAvailable=" + rtAvailable +
                ", isQoQWinner=" + isQoQWinner +
                ", isOffline=" + isOffline +
                ", isFullPrivatChat=" + isFullPrivatChat +
                ", isPrivatChat=" + isPrivatChat +
                ", isVipShow=" + isVipShow +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}

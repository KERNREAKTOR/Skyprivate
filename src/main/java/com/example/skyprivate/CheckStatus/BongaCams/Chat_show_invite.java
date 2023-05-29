package com.example.skyprivate.CheckStatus.BongaCams;

public class Chat_show_invite {
    private Boolean isAvailable;
    private String sourceType, avatarUrl, displayName;
    private Integer chatShowInvitationVersion;

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getChatShowInvitationVersion() {
        return chatShowInvitationVersion;
    }

    public void setChatShowInvitationVersion(Integer chatShowInvitationVersion) {
        this.chatShowInvitationVersion = chatShowInvitationVersion;
    }

    @Override
    public String toString() {
        return "Chat_show_invite{" +
                "isAvailable=" + isAvailable +
                ", sourceType='" + sourceType + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", displayName='" + displayName + '\'' +
                ", chatShowInvitationVersion=" + chatShowInvitationVersion +
                '}';
    }
}

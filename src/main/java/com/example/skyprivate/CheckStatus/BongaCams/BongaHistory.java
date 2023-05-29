package com.example.skyprivate.CheckStatus.BongaCams;

public class BongaHistory {
    private String username, gender, displayName, profileAvatarUrl, profileAvatarUrlMedium, profileAvatarUrlSmall;
    private boolean isOnline;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileAvatarUrl() {
        return profileAvatarUrl;
    }

    public void setProfileAvatarUrl(String profileAvatarUrl) {
        this.profileAvatarUrl = profileAvatarUrl;
    }

    public String getProfileAvatarUrlMedium() {
        return profileAvatarUrlMedium;
    }

    public void setProfileAvatarUrlMedium(String profileAvatarUrlMedium) {
        this.profileAvatarUrlMedium = profileAvatarUrlMedium;
    }

    public String getProfileAvatarUrlSmall() {
        return profileAvatarUrlSmall;
    }

    public void setProfileAvatarUrlSmall(String profileAvatarUrlSmall) {
        this.profileAvatarUrlSmall = profileAvatarUrlSmall;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}

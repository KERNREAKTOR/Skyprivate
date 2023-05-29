package com.example.skyprivate.CheckStatus.BongaCams;

public class Full_private_chat {
    private Boolean isAvailable, isPrivateChatPriceChanged, isAuthenticated;
    private Integer defaultTokensPerMinute, privateChatTokensPerMinute;
    private String videoMyUrl;

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Boolean getPrivateChatPriceChanged() {
        return isPrivateChatPriceChanged;
    }

    public void setPrivateChatPriceChanged(Boolean privateChatPriceChanged) {
        isPrivateChatPriceChanged = privateChatPriceChanged;
    }

    public Boolean getAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public Integer getDefaultTokensPerMinute() {
        return defaultTokensPerMinute;
    }

    public void setDefaultTokensPerMinute(Integer defaultTokensPerMinute) {
        this.defaultTokensPerMinute = defaultTokensPerMinute;
    }

    public Integer getPrivateChatTokensPerMinute() {
        return privateChatTokensPerMinute;
    }

    public void setPrivateChatTokensPerMinute(Integer privateChatTokensPerMinute) {
        this.privateChatTokensPerMinute = privateChatTokensPerMinute;
    }

    public String getVideoMyUrl() {
        return videoMyUrl;
    }

    public void setVideoMyUrl(String videoMyUrl) {
        this.videoMyUrl = videoMyUrl;
    }

    @Override
    public String toString() {
        return "Full_private_chat{" +
                "isAvailable=" + isAvailable +
                ", isPrivateChatPriceChanged=" + isPrivateChatPriceChanged +
                ", isAuthenticated=" + isAuthenticated +
                ", defaultTokensPerMinute=" + defaultTokensPerMinute +
                ", privateChatTokensPerMinute=" + privateChatTokensPerMinute +
                ", videoMyUrl='" + videoMyUrl + '\'' +
                '}';
    }
}

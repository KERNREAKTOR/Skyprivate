package com.example.skyprivate.CheckStatus.BongaCams;

public class Private_chat {
    private Boolean isAvailable, isPrivateChatPriceChanged;
    private Integer defaultTokensPerMinute, privateChatTokensPerMinute;

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

    @Override
    public String toString() {
        return "Private_chat{" +
                "isAvailable=" + isAvailable +
                ", isPrivateChatPriceChanged=" + isPrivateChatPriceChanged +
                ", defaultTokensPerMinute=" + defaultTokensPerMinute +
                ", privateChatTokensPerMinute=" + privateChatTokensPerMinute +
                '}';
    }
}

package com.example.skyprivate;

public class StripChatKingInfo {
    private int kingId, selfAmount, kingAmount, fanClubNumberMonthsOfSubscribed;
    private String kingUsername;
    private String fanClubTier;

    public int getKingId() {
        return kingId;
    }

    public void setKingId(int kingId) {
        this.kingId = kingId;
    }

    public int getSelfAmount() {
        return selfAmount;
    }

    public void setSelfAmount(int selfAmount) {
        this.selfAmount = selfAmount;
    }

    public int getKingAmount() {
        return kingAmount;
    }

    public void setKingAmount(int kingAmount) {
        this.kingAmount = kingAmount;
    }

    public int getFanClubNumberMonthsOfSubscribed() {
        return fanClubNumberMonthsOfSubscribed;
    }

    public void setFanClubNumberMonthsOfSubscribed(int fanClubNumberMonthsOfSubscribed) {
        this.fanClubNumberMonthsOfSubscribed = fanClubNumberMonthsOfSubscribed;
    }

    public String getKingUsername() {
        return kingUsername;
    }

    public void setKingUsername(String kingUsername) {
        this.kingUsername = kingUsername;
    }

    public String getFanClubTier() {
        return fanClubTier;
    }

    public void setFanClubTier(String fanClubTier) {
        this.fanClubTier = fanClubTier;
    }
}

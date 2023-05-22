package com.example.skyprivate;

public class StripChatCam {
    private boolean isLovenseEnabled, isMobile, isMuted, isCamActive;
    private int groupShowUsersCount, pricePerMessage, pricePerMedia, modelId, id;
    private String streamName;
    private StripChatKingInfo kingInfo;
    private StripChatGoal goal;

    public boolean isLovenseEnabled() {
        return isLovenseEnabled;
    }

    public void setLovenseEnabled(boolean lovenseEnabled) {
        isLovenseEnabled = lovenseEnabled;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public boolean isCamActive() {
        return isCamActive;
    }

    public void setCamActive(boolean camActive) {
        isCamActive = camActive;
    }

    public int getGroupShowUsersCount() {
        return groupShowUsersCount;
    }

    public void setGroupShowUsersCount(int groupShowUsersCount) {
        this.groupShowUsersCount = groupShowUsersCount;
    }

    public int getPricePerMessage() {
        return pricePerMessage;
    }

    public void setPricePerMessage(int pricePerMessage) {
        this.pricePerMessage = pricePerMessage;
    }

    public int getPricePerMedia() {
        return pricePerMedia;
    }

    public void setPricePerMedia(int pricePerMedia) {
        this.pricePerMedia = pricePerMedia;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public StripChatKingInfo getKingInfo() {
        return kingInfo;
    }

    public void setKingInfo(StripChatKingInfo kingInfo) {
        this.kingInfo = kingInfo;
    }

    public StripChatGoal getGoal() {
        return goal;
    }

    public void setGoal(StripChatGoal goal) {
        this.goal = goal;
    }
}

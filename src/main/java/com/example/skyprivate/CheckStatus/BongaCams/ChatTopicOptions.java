package com.example.skyprivate.CheckStatus.BongaCams;

public class ChatTopicOptions {
    private boolean isAvailable;
    private String currentTopic, displayName;

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

    @Override
    public String toString() {
        return "ChatTopicOptions{" +
                "isAvailable=" + isAvailable +
                ", currentTopic='" + currentTopic + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}

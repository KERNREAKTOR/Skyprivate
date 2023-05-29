package com.example.skyprivate.CheckStatus.BongaCams;

public class BongaChat {
    private ChatShowStatusOptions chatShowStatusOptions;
    private ChatTopicOptions chatTopicOptions;
    private TipPopupOptions tipPopupOptions;

    public PrivatePopupOptions getPrivatePopupOptions() {
        return privatePopupOptions;
    }

    public void setPrivatePopupOptions(PrivatePopupOptions privatePopupOptions) {
        this.privatePopupOptions = privatePopupOptions;
    }

    private PrivatePopupOptions privatePopupOptions;

    public TipPopupOptions getTipPopupOptions() {
        return tipPopupOptions;
    }

    public void setTipPopupOptions(TipPopupOptions tipPopupOptions) {
        this.tipPopupOptions = tipPopupOptions;
    }

    public ChatShowStatusOptions getChatShowStatusOptions() {
        return chatShowStatusOptions;
    }

    public void setChatShowStatusOptions(ChatShowStatusOptions chatShowStatusOptions) {
        this.chatShowStatusOptions = chatShowStatusOptions;
    }

    public ChatTopicOptions getChatTopicOptions() {
        return chatTopicOptions;
    }

    public void setChatTopicOptions(ChatTopicOptions chatTopicOptions) {
        this.chatTopicOptions = chatTopicOptions;
    }
}

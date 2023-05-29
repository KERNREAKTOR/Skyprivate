package com.example.skyprivate.CheckStatus.BongaCams;

public class PrivatePopupOptions {
    private Boolean isAvailable;
    private Group_chat group_chat;
    private Private_chat private_chat;
    private Full_private_chat full_private_chat;
    private Voyeur_chat voyeur_chat;
    private Chat_show_invite chat_show_invite;
    private String culture;

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Group_chat getGroup_chat() {
        return group_chat;
    }

    public void setGroup_chat(Group_chat group_chat) {
        this.group_chat = group_chat;
    }

    public Private_chat getPrivate_chat() {
        return private_chat;
    }

    public void setPrivate_chat(Private_chat private_chat) {
        this.private_chat = private_chat;
    }

    public Full_private_chat getFull_private_chat() {
        return full_private_chat;
    }

    public void setFull_private_chat(Full_private_chat full_private_chat) {
        this.full_private_chat = full_private_chat;
    }

    public Voyeur_chat getVoyeur_chat() {
        return voyeur_chat;
    }

    public void setVoyeur_chat(Voyeur_chat voyeur_chat) {
        this.voyeur_chat = voyeur_chat;
    }

    public Chat_show_invite getChat_show_invite() {
        return chat_show_invite;
    }

    public void setChat_show_invite(Chat_show_invite chat_show_invite) {
        this.chat_show_invite = chat_show_invite;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    @Override
    public String toString() {
        return "PrivatePopupOptions{" +
                "isAvailable=" + isAvailable +
                ", group_chat=" + group_chat +
                ", private_chat=" + private_chat +
                ", full_private_chat=" + full_private_chat +
                ", voyeur_chat=" + voyeur_chat +
                ", chat_show_invite=" + chat_show_invite +
                ", culture='" + culture + '\'' +
                '}';
    }
}

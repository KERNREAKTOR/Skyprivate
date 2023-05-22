package com.example.skyprivate;

public class StripChatUser {
    private boolean isStudioAdmin, hasNonNudeProfileAccess, isSubscribed;
    private String previewReviewStatus;
    private SocialLinks socialLinks;
    private StripChatCamUserUser user;
    //private StripChatCam cam;
    private int currPosition, currPoints, videosCount, photosCount;

//    public StripChatCam getCam() {
//        return cam;
//    }
//
//    public void setCam(StripChatCam cam) {
//        this.cam = cam;
//    }

    public SocialLinks getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(SocialLinks socialLinks) {
        this.socialLinks = socialLinks;
    }

    public StripChatCamUserUser getUser() {
        return user;
    }

    public void setUser(StripChatCamUserUser user) {
        this.user = user;
    }

    public boolean isStudioAdmin() {
        return isStudioAdmin;
    }

    public void setStudioAdmin(boolean studioAdmin) {
        isStudioAdmin = studioAdmin;
    }

    public boolean isHasNonNudeProfileAccess() {
        return hasNonNudeProfileAccess;
    }

    public void setHasNonNudeProfileAccess(boolean hasNonNudeProfileAccess) {
        this.hasNonNudeProfileAccess = hasNonNudeProfileAccess;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public String getPreviewReviewStatus() {
        return previewReviewStatus;
    }

    public void setPreviewReviewStatus(String previewReviewStatus) {
        this.previewReviewStatus = previewReviewStatus;
    }

    public int getCurrPosition() {
        return currPosition;
    }

    public void setCurrPosition(int currPosition) {
        this.currPosition = currPosition;
    }

    public int getCurrPoints() {
        return currPoints;
    }

    public void setCurrPoints(int currPoints) {
        this.currPoints = currPoints;
    }

    public int getVideosCount() {
        return videosCount;
    }

    public void setVideosCount(int videosCount) {
        this.videosCount = videosCount;
    }

    public int getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(int photosCount) {
        this.photosCount = photosCount;
    }


}

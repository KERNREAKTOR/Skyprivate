package com.example.skyprivate.CheckStatus.BongaCams.CheckStatus;

public class StreamInfo {
    Integer bandWith;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    Integer height;
    Integer width;
    String resolution, Codecs, chunkLink;

    public Integer getBandWith() {
        return bandWith;
    }

    public void setBandWith(Integer bandWith) {
        this.bandWith = bandWith;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getCodecs() {
        return Codecs;
    }

    public void setCodecs(String codecs) {
        Codecs = codecs;
    }

    public String getChunkLink() {
        return chunkLink;
    }

    public void setChunkLink(String chunkLink) {
        this.chunkLink = chunkLink;
    }
}

package com.example.minitumblr.view.model;

public class VideoPostVM extends PostVM {
    private String embedCode;
    private String caption;

    public String getEmbedCode() {
        return embedCode;
    }

    public void setEmbedCode(String embedCode) {
        this.embedCode = embedCode;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}

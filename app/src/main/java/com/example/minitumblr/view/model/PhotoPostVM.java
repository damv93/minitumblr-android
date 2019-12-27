package com.example.minitumblr.view.model;

import java.util.List;

public class PhotoPostVM extends PostVM {
    private String caption;
    private List<PhotoVM> photos;

    public List<PhotoVM> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoVM> photos) {
        this.photos = photos;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}

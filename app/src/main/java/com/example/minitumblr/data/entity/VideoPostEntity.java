package com.example.minitumblr.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "VIDEO_POST")
public class VideoPostEntity {
    @Id(autoincrement = true)
    private Long id;
    private String embedCode;
    private String caption;
    @Generated(hash = 161899386)
    public VideoPostEntity(Long id, String embedCode, String caption) {
        this.id = id;
        this.embedCode = embedCode;
        this.caption = caption;
    }
    @Generated(hash = 1943205011)
    public VideoPostEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmbedCode() {
        return this.embedCode;
    }
    public void setEmbedCode(String embedCode) {
        this.embedCode = embedCode;
    }
    public String getCaption() {
        return this.caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
}

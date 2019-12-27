package com.example.minitumblr.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "PHOTO")
public class PhotoEntity {
    @Id(autoincrement = true)
    private Long id;
    private String url;
    private Long postId;
    @Generated(hash = 1994170620)
    public PhotoEntity(Long id, String url, Long postId) {
        this.id = id;
        this.url = url;
        this.postId = postId;
    }
    @Generated(hash = 1889335700)
    public PhotoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Long getPostId() {
        return this.postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
}

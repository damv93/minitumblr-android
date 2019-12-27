package com.example.minitumblr.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "TEXT_POST")
public class TextPostEntity extends PostEntity {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String body;
    @Generated(hash = 1477515470)
    public TextPostEntity(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
    @Generated(hash = 66924959)
    public TextPostEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return this.body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}

package com.example.minitumblr.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "POST")
public class PostEntity {
    @Id(autoincrement = true)
    private Long id;
    private Long postId;
    private String blogName;
    private Long noteCount;

    private Long textPostId;
    @ToOne(joinProperty = "textPostId")
    private TextPostEntity textPost;

    private Long photoPostId;
    @ToOne(joinProperty = "photoPostId")
    private PhotoPostEntity photoPost;

    private Long videoPostId;
    @ToOne(joinProperty = "videoPostId")
    private VideoPostEntity videoPost;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1666881267)
    private transient PostEntityDao myDao;
    @Generated(hash = 1142804511)
    public PostEntity(Long id, Long postId, String blogName, Long noteCount,
            Long textPostId, Long photoPostId, Long videoPostId) {
        this.id = id;
        this.postId = postId;
        this.blogName = blogName;
        this.noteCount = noteCount;
        this.textPostId = textPostId;
        this.photoPostId = photoPostId;
        this.videoPostId = videoPostId;
    }
    @Generated(hash = 1061262511)
    public PostEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPostId() {
        return this.postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public String getBlogName() {
        return this.blogName;
    }
    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }
    public Long getNoteCount() {
        return this.noteCount;
    }
    public void setNoteCount(Long noteCount) {
        this.noteCount = noteCount;
    }
    public Long getTextPostId() {
        return this.textPostId;
    }
    public void setTextPostId(Long textPostId) {
        this.textPostId = textPostId;
    }
    public Long getPhotoPostId() {
        return this.photoPostId;
    }
    public void setPhotoPostId(Long photoPostId) {
        this.photoPostId = photoPostId;
    }
    public Long getVideoPostId() {
        return this.videoPostId;
    }
    public void setVideoPostId(Long videoPostId) {
        this.videoPostId = videoPostId;
    }
    @Generated(hash = 2113407214)
    private transient Long textPost__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 251923556)
    public TextPostEntity getTextPost() {
        Long __key = this.textPostId;
        if (textPost__resolvedKey == null || !textPost__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TextPostEntityDao targetDao = daoSession.getTextPostEntityDao();
            TextPostEntity textPostNew = targetDao.load(__key);
            synchronized (this) {
                textPost = textPostNew;
                textPost__resolvedKey = __key;
            }
        }
        return textPost;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1553924518)
    public void setTextPost(TextPostEntity textPost) {
        synchronized (this) {
            this.textPost = textPost;
            textPostId = textPost == null ? null : textPost.getId();
            textPost__resolvedKey = textPostId;
        }
    }
    @Generated(hash = 1811190056)
    private transient Long photoPost__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 980222928)
    public PhotoPostEntity getPhotoPost() {
        Long __key = this.photoPostId;
        if (photoPost__resolvedKey == null
                || !photoPost__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PhotoPostEntityDao targetDao = daoSession.getPhotoPostEntityDao();
            PhotoPostEntity photoPostNew = targetDao.load(__key);
            synchronized (this) {
                photoPost = photoPostNew;
                photoPost__resolvedKey = __key;
            }
        }
        return photoPost;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1509743731)
    public void setPhotoPost(PhotoPostEntity photoPost) {
        synchronized (this) {
            this.photoPost = photoPost;
            photoPostId = photoPost == null ? null : photoPost.getId();
            photoPost__resolvedKey = photoPostId;
        }
    }
    @Generated(hash = 659488917)
    private transient Long videoPost__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 445554270)
    public VideoPostEntity getVideoPost() {
        Long __key = this.videoPostId;
        if (videoPost__resolvedKey == null
                || !videoPost__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VideoPostEntityDao targetDao = daoSession.getVideoPostEntityDao();
            VideoPostEntity videoPostNew = targetDao.load(__key);
            synchronized (this) {
                videoPost = videoPostNew;
                videoPost__resolvedKey = __key;
            }
        }
        return videoPost;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1466255846)
    public void setVideoPost(VideoPostEntity videoPost) {
        synchronized (this) {
            this.videoPost = videoPost;
            videoPostId = videoPost == null ? null : videoPost.getId();
            videoPost__resolvedKey = videoPostId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 643091034)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPostEntityDao() : null;
    }
}

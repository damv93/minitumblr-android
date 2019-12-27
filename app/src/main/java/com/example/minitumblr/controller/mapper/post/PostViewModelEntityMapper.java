package com.example.minitumblr.controller.mapper.post;

import com.example.minitumblr.data.entity.PhotoEntity;
import com.example.minitumblr.data.entity.PhotoPostEntity;
import com.example.minitumblr.data.entity.PostEntity;
import com.example.minitumblr.data.entity.TextPostEntity;
import com.example.minitumblr.data.entity.VideoPostEntity;
import com.example.minitumblr.view.model.PhotoPostVM;
import com.example.minitumblr.view.model.PhotoVM;
import com.example.minitumblr.view.model.PostVM;
import com.example.minitumblr.view.model.TextPostVM;
import com.example.minitumblr.view.model.VideoPostVM;
import com.tumblr.jumblr.types.Photo;

import java.util.ArrayList;
import java.util.List;

public class PostViewModelEntityMapper {

    public static PostVM map(PostEntity postEntity) {
        PostVM postVM;
        if (postEntity.getTextPost() != null)
            postVM = map(postEntity.getTextPost());
        else if (postEntity.getPhotoPost() != null)
            postVM = map(postEntity.getPhotoPost());
        else if (postEntity.getVideoPost() != null)
            postVM = map(postEntity.getVideoPost());
        else
            postVM = new PostVM();
        postVM.setPostId(postEntity.getPostId());
        postVM.setBlogName(postEntity.getBlogName());
        postVM.setNoteCount(postEntity.getNoteCount());
        return postVM;
    }

    public static List<PostVM> map(List<PostEntity> postEntities) {
        List<PostVM> postVMs = new ArrayList<>();
        for (PostEntity postEntity : postEntities) {
            PostVM postVM = map(postEntity);
            postVMs.add(postVM);
        }
        return postVMs;
    }

    private static TextPostVM map(TextPostEntity textPostEntity) {
        TextPostVM textPostVM = new TextPostVM();
        textPostVM.setTitle(textPostEntity.getTitle());
        textPostVM.setBody(textPostEntity.getBody());
        return textPostVM;
    }

    private static PhotoPostVM map(PhotoPostEntity photoPostEntity) {
        PhotoPostVM photoPostVM = new PhotoPostVM();
        photoPostVM.setCaption(photoPostEntity.getCaption());
        List<PhotoVM> photoVMs = new ArrayList<>();
        for (PhotoEntity photoEntity : photoPostEntity.getPhotos()) {
            PhotoVM photoVM = new PhotoVM();
            photoVM.setUrl(photoEntity.getUrl());
            photoVMs.add(photoVM);
        }
        photoPostVM.setPhotos(photoVMs);
        return photoPostVM;
    }

    private static VideoPostVM map(VideoPostEntity videoPostEntity) {
        VideoPostVM videoPostVM = new VideoPostVM();
        videoPostVM.setCaption(videoPostEntity.getCaption());
        videoPostVM.setEmbedCode(videoPostEntity.getEmbedCode());
        return videoPostVM;
    }
}

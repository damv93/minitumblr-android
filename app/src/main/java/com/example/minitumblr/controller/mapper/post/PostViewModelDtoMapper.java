package com.example.minitumblr.controller.mapper.post;

import com.example.minitumblr.view.model.PhotoPostVM;
import com.example.minitumblr.view.model.PhotoVM;
import com.example.minitumblr.view.model.PostVM;
import com.example.minitumblr.view.model.TextPostVM;
import com.example.minitumblr.view.model.VideoPostVM;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.VideoPost;

import java.util.ArrayList;
import java.util.List;

public class PostViewModelDtoMapper {

    public static PostVM map(Post postDTO) {
        PostVM postVM;
        if (postDTO instanceof TextPost)
            postVM = map((TextPost) postDTO);
        else if (postDTO instanceof PhotoPost)
            postVM = map((PhotoPost) postDTO);
        else if (postDTO instanceof VideoPost)
            postVM = map((VideoPost) postDTO);
        else
            postVM = new PostVM();
        postVM.setPostId(postDTO.getId());
        postVM.setBlogName(postDTO.getBlogName());
        postVM.setNoteCount(postDTO.getNoteCount());
        return postVM;
    }

    public static List<PostVM> map(List<Post> postDTOs) {
        List<PostVM> postVMs = new ArrayList<>();
        for (Post postDTO : postDTOs) {
            PostVM postVM = map(postDTO);
            postVMs.add(postVM);
        }
        return postVMs;
    }

    private static TextPostVM map(TextPost textPostDTO) {
        TextPostVM textPostVM = new TextPostVM();
        textPostVM.setTitle(textPostDTO.getTitle());
        textPostVM.setBody(textPostDTO.getBody());
        return textPostVM;
    }

    private static PhotoPostVM map(PhotoPost photoPostDTO) {
        PhotoPostVM photoPostVM = new PhotoPostVM();
        photoPostVM.setCaption(photoPostDTO.getCaption());
        List<PhotoVM> photoVMs = new ArrayList<>();
        for (Photo photoDTO : photoPostDTO.getPhotos()) {
            PhotoVM photoVM = new PhotoVM();
            photoVM.setUrl(photoDTO.getOriginalSize().getUrl());
            photoVMs.add(photoVM);
        }
        photoPostVM.setPhotos(photoVMs);
        return photoPostVM;
    }

    private static VideoPostVM map(VideoPost videoPostDTO) {
        VideoPostVM videoPostVM = new VideoPostVM();
        videoPostVM.setCaption(videoPostDTO.getCaption());
        videoPostVM.setEmbedCode(videoPostDTO.getVideos().get(0).getEmbedCode());
        return videoPostVM;
    }
}

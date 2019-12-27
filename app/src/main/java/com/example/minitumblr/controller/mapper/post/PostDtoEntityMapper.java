package com.example.minitumblr.controller.mapper.post;

import com.example.minitumblr.data.entity.PhotoPostEntity;
import com.example.minitumblr.data.entity.PostEntity;
import com.example.minitumblr.data.entity.TextPostEntity;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import java.util.ArrayList;
import java.util.List;

public class PostDtoEntityMapper {

    public static PostEntity map(Post postDTO) {
        PostEntity postEntity = new PostEntity();
        postEntity.setPostId(postDTO.getId());
        postEntity.setBlogName(postDTO.getBlogName());
        postEntity.setNoteCount(postDTO.getNoteCount());
        return postEntity;
    }

    public static List<PostEntity> map(List<Post> postDTOs) {
        List<PostEntity> postEntities = new ArrayList<>();
        for (Post postDTO : postDTOs) {
            PostEntity postEntity = map(postDTO);
            postEntities.add(postEntity);
        }
        return postEntities;
    }

    public static Post map(PostEntity postEntity) {
        Post postDTO = new Post();
        postDTO.setId(postEntity.getPostId());
        postDTO.setBlogName(postEntity.getBlogName());
        return postDTO;
    }

}

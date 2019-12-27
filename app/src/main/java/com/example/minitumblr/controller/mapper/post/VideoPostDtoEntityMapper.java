package com.example.minitumblr.controller.mapper.post;

import com.example.minitumblr.data.entity.VideoPostEntity;
import com.tumblr.jumblr.types.VideoPost;

public class VideoPostDtoEntityMapper {

    public static VideoPostEntity map(VideoPost videoPostDTO) {
        VideoPostEntity videoPostEntity = new VideoPostEntity();
        videoPostEntity.setCaption(videoPostDTO.getCaption());
        videoPostEntity.setEmbedCode(videoPostDTO.getVideos().get(0).getEmbedCode());
        return videoPostEntity;
    }

}

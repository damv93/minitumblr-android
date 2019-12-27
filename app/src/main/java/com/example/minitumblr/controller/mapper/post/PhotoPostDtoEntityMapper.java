package com.example.minitumblr.controller.mapper.post;

import com.example.minitumblr.data.entity.PhotoPostEntity;
import com.tumblr.jumblr.types.PhotoPost;

public class PhotoPostDtoEntityMapper {

    public static PhotoPostEntity map(PhotoPost photoPostDTO) {
        PhotoPostEntity photoPostEntity = new PhotoPostEntity();
        photoPostEntity.setCaption(photoPostDTO.getCaption());
        return photoPostEntity;
    }

}

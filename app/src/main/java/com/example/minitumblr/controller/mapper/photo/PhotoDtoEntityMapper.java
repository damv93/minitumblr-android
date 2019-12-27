package com.example.minitumblr.controller.mapper.photo;

import com.example.minitumblr.data.entity.PhotoEntity;
import com.tumblr.jumblr.types.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoDtoEntityMapper {

    public static PhotoEntity map(Photo photoDTO, long postId) {
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setPostId(postId);
        photoEntity.setUrl(photoDTO.getOriginalSize().getUrl());
        return photoEntity;
    }

    public static List<PhotoEntity> map(List<Photo> photoDTOs, long postId) {
        List<PhotoEntity> photoEntities = new ArrayList<>();
        for (Photo photoDTO : photoDTOs) {
            PhotoEntity photoEntity = map(photoDTO, postId);
            photoEntities.add(photoEntity);
        }
        return photoEntities;
    }

}

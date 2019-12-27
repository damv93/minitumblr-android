package com.example.minitumblr.controller.mapper.post;

import com.example.minitumblr.data.entity.TextPostEntity;
import com.tumblr.jumblr.types.TextPost;

public class TextPostDtoEntityMapper {

    public static TextPostEntity map(TextPost textPostDTO) {
        TextPostEntity textPostEntity = new TextPostEntity();
        textPostEntity.setTitle(textPostDTO.getTitle());
        textPostEntity.setBody(textPostDTO.getBody());
        return textPostEntity;
    }
}

package com.example.minitumblr.view;

import com.example.minitumblr.view.model.PostVM;

import java.util.List;
import java.util.Map;

public interface DashboardView extends BaseView {
    void onGetPosts(List<PostVM> posts, Map<String, String> avatars, String errorMessage);
    void onGetMorePosts(List<PostVM> posts, Map<String, String> avatars, String errorMessage);
    void showLoadingMore();
    void hideLoadingMore();
}

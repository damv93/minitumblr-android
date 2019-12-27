package com.example.minitumblr.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.minitumblr.MiniTumblrAppData;
import com.example.minitumblr.controller.mapper.photo.PhotoDtoEntityMapper;
import com.example.minitumblr.controller.mapper.post.PhotoPostDtoEntityMapper;
import com.example.minitumblr.controller.mapper.post.PostDtoEntityMapper;
import com.example.minitumblr.controller.mapper.post.PostViewModelDtoMapper;
import com.example.minitumblr.controller.mapper.post.PostViewModelEntityMapper;
import com.example.minitumblr.controller.mapper.post.TextPostDtoEntityMapper;
import com.example.minitumblr.controller.mapper.post.VideoPostDtoEntityMapper;
import com.example.minitumblr.data.entity.DaoSession;
import com.example.minitumblr.data.entity.PhotoEntity;
import com.example.minitumblr.data.entity.PhotoEntityDao;
import com.example.minitumblr.data.entity.PhotoPostEntity;
import com.example.minitumblr.data.entity.PhotoPostEntityDao;
import com.example.minitumblr.data.entity.PostEntity;
import com.example.minitumblr.data.entity.PostEntityDao;
import com.example.minitumblr.data.entity.TextPostEntity;
import com.example.minitumblr.data.entity.TextPostEntityDao;
import com.example.minitumblr.data.entity.VideoPostEntity;
import com.example.minitumblr.data.entity.VideoPostEntityDao;
import com.example.minitumblr.view.DashboardView;
import com.example.minitumblr.view.model.PostVM;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.Video;
import com.tumblr.jumblr.types.VideoPost;

import org.scribe.exceptions.OAuthException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController extends Controller {

    private DashboardView mView;
    private JumblrClient mClient;
    private final DaoSession mDaoSession;
    private PostEntityDao mPostEntityDao;
    private TextPostEntityDao mTextPostEntityDao;
    private PhotoPostEntityDao mPhotoPostEntityDao;
    private PhotoEntityDao mPhotoEntityDao;
    private VideoPostEntityDao mVideoPostEntityDao;

    public DashboardController(Context context, DashboardView view) {
        super(context);

        mView = view;

        mClient = new JumblrClient(
                "cO73Z1vnUWgIeD27e6Cl8zVt2bWygfPsJ5WXbonQKAhoEMRpM6",
                "MXRFMassEiC6Vdq9pgpZAkdPu3WNkWl4ZwpH1E7ExIQDFnyk0E"
        );
        mClient.setToken(
                "rZXeYQgkGVaczMBwJCjfwe0fBaxckHogOIJYrw2laREMr0DNMi",
                "uDxWlWbokYySTBPicYUTahdGRiwyNxlTgZCIUHt6EoCkEIMzCL"
        );

        mDaoSession = ((MiniTumblrAppData) mContext.getApplicationContext()).getDaoSession();
        mPostEntityDao = mDaoSession.getPostEntityDao();
        mTextPostEntityDao = mDaoSession.getTextPostEntityDao();
        mPhotoPostEntityDao = mDaoSession.getPhotoPostEntityDao();
        mPhotoEntityDao = mDaoSession.getPhotoEntityDao();
        mVideoPostEntityDao = mDaoSession.getVideoPostEntityDao();
    }

    public void getPosts() {

        Handler responseHandler = new Handler(Looper.getMainLooper()) {
            @Override
            @SuppressWarnings("unchecked")
            public void handleMessage(Message msg) {
                mView.hideLoading();
                Map<String, Object> result = (Map<String, Object>) msg.obj;
                List<PostVM> postVMs = (List<PostVM>) result.get("posts");
                Map<String, String> avatars = (Map<String, String>) result.get("avatars");
                String errorMessage = result.containsKey("error") ? (String) result.get("error") : "";
                mView.onGetPosts(postVMs, avatars, errorMessage);
            }
        };

        mView.showLoading();

        Runnable runGetPosts = () -> {

            List<PostVM> postVMs;
            Map<String, String> avatars = new HashMap<>();
            Map<String, Object> result = new HashMap<>();

            try {

                Map<String, Integer> options = new HashMap<>();
                options.put("limit", 10);
                List<Post> postDTOs = mClient.userDashboard(options);

                postVMs = PostViewModelDtoMapper.map(postDTOs); // map post DTOs to View Models

                for (Post postDTO : postDTOs) {
                    String blogName = postDTO.getBlogName();
                    if (!avatars.containsKey(blogName)) {
                        Blog blog = mClient.blogInfo(blogName);
                        avatars.put(blogName, blog.avatar());
                    }
                }

                new Thread(() -> savePosts(postDTOs, avatars)).start(); // save posts in database

            } catch (OAuthException | JumblrException e) {
                postVMs = loadPostVMs(); // get posts from database and map them to View Models
                result.put("error", "Ocurrió un error en la conexión. Vuelva a intentarlo");
            }

            result.put("posts", postVMs);
            result.put("avatars", avatars);

            Message msg = new Message();
            msg.obj = result;
            responseHandler.sendMessage(msg);
        };

        new Thread(runGetPosts).start();
    }

    public void getMorePosts(Map<String, Integer> options) {

        Handler responseHandler = new Handler(Looper.getMainLooper()) {
            @Override
            @SuppressWarnings("unchecked")
            public void handleMessage(Message msg) {
                mView.hideLoadingMore();
                Map<String, Object> result = (Map<String, Object>) msg.obj;
                List<PostVM> postVMs = (List<PostVM>) result.get("posts");
                Map<String, String> avatars = (Map<String, String>) result.get("avatars");
                String errorMessage = result.containsKey("error") ? (String) result.get("error") : "";
                mView.onGetMorePosts(postVMs, avatars, errorMessage);
            }
        };

        mView.showLoadingMore();

        new Thread(() -> {

            List<PostVM> postVMs;
            Map<String, String> avatars = new HashMap<>();
            Map<String, Object> result = new HashMap<>();

            try {

                options.put("limit", 10);
                List<Post> postDTOs = mClient.userDashboard(options);

                postVMs = PostViewModelDtoMapper.map(postDTOs); // map post DTOs to View Models

                for (Post postDTO : postDTOs) {
                    String blogName = postDTO.getBlogName();
                    if (!avatars.containsKey(blogName)) {
                        Blog blog = mClient.blogInfo(blogName);
                        avatars.put(blogName, blog.avatar());
                    }
                }

            } catch (OAuthException | JumblrException e) {
                postVMs = new ArrayList<>();
                result.put("error", "Ocurrió un error en la conexión. Vuelva a intentarlo");
            }

            result.put("posts", postVMs);
            result.put("avatars", avatars);

            Message msg = new Message();
            msg.obj = result;
            responseHandler.sendMessage(msg);

        }).start();

    }

    private void deletePostEntities() {
        mPostEntityDao.deleteAll();
        mTextPostEntityDao.deleteAll();
        mPhotoEntityDao.deleteAll();
        mPhotoPostEntityDao.deleteAll();
    }

    private List<PostEntity> loadPostEntities() {
        return mPostEntityDao.loadAll();
    }

    private void savePosts(List<Post> postDTOs, Map<String, String> avatars) {
        deletePostEntities(); // clear post database

        for (Post postDTO : postDTOs) {
            PostEntity postEntity = PostDtoEntityMapper.map(postDTO);
            if (postDTO instanceof TextPost) {
                TextPostEntity textPostEntity = TextPostDtoEntityMapper.map((TextPost) postDTO);
                long id = mTextPostEntityDao.insert(textPostEntity);
                postEntity.setTextPostId(id);
            } else if (postDTO instanceof PhotoPost) {
                PhotoPost photoPostDTO = (PhotoPost) postDTO;
                PhotoPostEntity photoPostEntity = PhotoPostDtoEntityMapper.map(photoPostDTO);
                long id = mPhotoPostEntityDao.insert(photoPostEntity);
                List<PhotoEntity> photoEntities = PhotoDtoEntityMapper.map(photoPostDTO.getPhotos(), id);
                mPhotoEntityDao.insertInTx(photoEntities);
                postEntity.setPhotoPostId(id);
            } else if (postDTO instanceof VideoPost) {
                VideoPostEntity videoPostEntity = VideoPostDtoEntityMapper.map((VideoPost) postDTO);
                long id = mVideoPostEntityDao.insert(videoPostEntity);
                postEntity.setVideoPostId(id);
            }
            mPostEntityDao.insertInTx(postEntity);
        }
    }

    private List<PostVM> loadPostVMs() {
        List<PostEntity> postEntities = loadPostEntities();
        return PostViewModelEntityMapper.map(postEntities);
    }
}

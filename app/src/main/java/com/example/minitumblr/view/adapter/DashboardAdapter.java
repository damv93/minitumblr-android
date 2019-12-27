package com.example.minitumblr.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minitumblr.R;
import com.example.minitumblr.view.model.PhotoPostVM;
import com.example.minitumblr.view.model.PhotoVM;
import com.example.minitumblr.view.model.PostVM;
import com.example.minitumblr.view.model.TextPostVM;
import com.example.minitumblr.view.model.VideoPostVM;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.PostViewHolder> {

    private Context mContext;
    private List<PostVM> mPosts;
    private Map<String, String> mAvatars;

    public DashboardAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<PostVM> posts, Map<String, String> avatars) {
        mPosts = posts;
        mAvatars = avatars;
        notifyDataSetChanged();
    }

    public void addData(List<PostVM> posts, Map<String, String> avatars) {
        if (mPosts == null) mPosts = posts;
        else mPosts.addAll(posts);


        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostVM post = mPosts.get(position);
        String blogName = post.getBlogName();

        Picasso.get()
                .load(mAvatars.get(blogName))
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_person_black_24dp)
                .into(holder.imgAvatar);
        holder.tvBlogName.setText(post.getBlogName());

        boolean showText = false, showPhoto = false, showVideo = false, showNotAvailable = false;

        if (post instanceof TextPostVM) {
            showText = true;
            TextPostVM textPost = (TextPostVM) post;
            setTextPostView(textPost, holder);
        } else if (post instanceof PhotoPostVM) {
            showPhoto = true;
            PhotoPostVM photoPost = (PhotoPostVM) post;
            setPhotoPostView(photoPost, holder);
        } else if (post instanceof VideoPostVM) {
            showVideo = true;
            VideoPostVM videoPost = (VideoPostVM) post;
            setVideoPostView(videoPost, holder);
        } else showNotAvailable = true;

        holder.vTextPost.setVisibility(showText ? View.VISIBLE : View.GONE);
        holder.vPhotoPost.setVisibility(showPhoto ? View.VISIBLE : View.GONE);
        holder.vVideoPost.setVisibility(showVideo ? View.VISIBLE : View.GONE);
        holder.tvContentNotAvailable.setVisibility(showNotAvailable ? View.VISIBLE : View.GONE);

        Long noteCount = post.getNoteCount();
        String noteCountFormatted = noteCount != null ?
                NumberFormat.getInstance().format(noteCount) : "0";
        holder.tvNotes.setText(mContext.getString(R.string.txt_notes, noteCountFormatted));
    }

    @Override
    public int getItemCount() {
        return mPosts == null ? 0 : mPosts.size();
    }

    private void setTextPostView(TextPostVM post, PostViewHolder holder) {
        TextView tvTitle = holder.vTextPost.findViewById(R.id.tv_title);
        String title = post.getTitle();
        if (TextUtils.isEmpty(title))
            tvTitle.setVisibility(View.GONE);
        else {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }

        String html = "<link rel='stylesheet' type='text/css' href='webview_style.css' />"
                + post.getBody();
        WebView wvTextPost = holder.vTextPost.findViewById(R.id.wv_text_post);
        wvTextPost.getSettings().setJavaScriptEnabled(true);
        wvTextPost.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
    }

    private void setPhotoPostView(PhotoPostVM post, PostViewHolder holder) {
        List<PhotoVM> photos = post.getPhotos();

        ViewPager vpSlider = holder.vPhotoPost.findViewById(R.id.vp_slider);
        SliderAdapter sliderAdapter = new SliderAdapter(mContext);
        sliderAdapter.setPhotos(photos);
        vpSlider.setAdapter(sliderAdapter);

        CircleIndicator ciSlider = holder.vPhotoPost.findViewById(R.id.ci_slider);
        ciSlider.setViewPager(vpSlider);
        ciSlider.setVisibility(photos.size() > 1 ? View.VISIBLE : View.GONE);

        WebView wvCaption = holder.vPhotoPost.findViewById(R.id.wv_caption);
        String caption = post.getCaption();
        if (TextUtils.isEmpty(caption))
            wvCaption.setVisibility(View.GONE);
        else {
            wvCaption.loadData(caption, "text/html", "UTF-8");
            wvCaption.setVisibility(View.VISIBLE);
        }
    }

    private void setVideoPostView(VideoPostVM post, PostViewHolder holder) {
        String html = "<link rel='stylesheet' type='text/css' href='webview_style.css' />"
                + post.getEmbedCode() + post.getCaption();
        WebView wvVideoPost = (WebView) holder.vVideoPost;
        if (post.getEmbedCode().contains("iframe"))
            wvVideoPost.getSettings().setJavaScriptEnabled(true);
        wvVideoPost.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.crl_post)
        ConstraintLayout crlPost;
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_blog_name)
        TextView tvBlogName;
        @BindView(R.id.v_text_post)
        View vTextPost;
        @BindView(R.id.v_photo_post)
        View vPhotoPost;
        @BindView(R.id.v_video_post)
        View vVideoPost;
        @BindView(R.id.tv_content_not_available)
        TextView tvContentNotAvailable;
        @BindView(R.id.tv_notes)
        TextView tvNotes;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

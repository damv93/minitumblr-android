package com.example.minitumblr.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minitumblr.R;
import com.example.minitumblr.controller.DashboardController;
import com.example.minitumblr.view.DashboardView;
import com.example.minitumblr.view.adapter.DashboardAdapter;
import com.example.minitumblr.view.model.PostVM;
import com.google.android.material.snackbar.Snackbar;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment implements DashboardView, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.srl_dashboard)
    SwipeRefreshLayout srlDashboard;
    @BindView(R.id.rv_dashboard)
    RecyclerView rvDashboard;
    @BindView(R.id.tv_error_message)
    TextView tvErrorMessage;
    @BindView(R.id.pb_load_more)
    ProgressBar pbLoadMore;

    private DashboardController mController;

    private DashboardAdapter mAdapter;
    private List<PostVM> mPosts;
    private  boolean isLoadingMore;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DashboardFragment.
     */
    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);

        srlDashboard.setColorSchemeResources(R.color.colorAccent);
        srlDashboard.setOnRefreshListener(this);

        rvDashboard.setHasFixedSize(true);
        mAdapter = new DashboardAdapter(getContext());
        rvDashboard.setAdapter(mAdapter);
        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        rvDashboard.setLayoutManager(llManager);
        rvDashboard.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = llManager.getItemCount();
                int lastVisibleItemPos = llManager.findLastVisibleItemPosition();
                int visibleThreshold = 1;
                if (!isLoadingMore && totalItemCount <= lastVisibleItemPos + visibleThreshold) {
                    isLoadingMore = true;
                    Map<String, Integer> options = new HashMap<>();
                    options.put("offset", totalItemCount);
                    mController.getMorePosts(options);

                }
            }
        });

        mController = new DashboardController(getContext(), this);
        mController.getPosts();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onGetPosts(List<PostVM> posts, Map<String, String> avatars, String errorMessage) {
        mAdapter.setData(posts, avatars);
        if (posts == null || posts.isEmpty()) {
            rvDashboard.setVisibility(View.GONE);
            tvErrorMessage.setText(R.string.msg_no_posts);
            tvErrorMessage.setVisibility(View.VISIBLE);
        } else {
            rvDashboard.setVisibility(View.VISIBLE);
            tvErrorMessage.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(errorMessage) && getContext() != null)
            showSnackBar(errorMessage);
    }

    @Override
    public void onGetMorePosts(List<PostVM> posts, Map<String, String> avatars, String errorMessage) {
        if (posts != null && !posts.isEmpty())
            mAdapter.addData(posts, avatars);

        if (!TextUtils.isEmpty(errorMessage) && getContext() != null)
            showSnackBar(errorMessage);

        isLoadingMore = false;
    }

    @Override
    public void onRefresh() {
        mController.getPosts();
    }

    @Override
    public void showLoading() {
        tvErrorMessage.setVisibility(View.GONE);
        srlDashboard.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        srlDashboard.setRefreshing(false);
    }

    @Override
    public void showLoadingMore() {
        pbLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingMore() {
        pbLoadMore.setVisibility(View.GONE);
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        snackbar.show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
    }
}

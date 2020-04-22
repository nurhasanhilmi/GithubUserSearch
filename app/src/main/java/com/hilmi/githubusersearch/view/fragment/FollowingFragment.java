package com.hilmi.githubusersearch.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hilmi.githubusersearch.R;
import com.hilmi.githubusersearch.adapter.ListFollowingAdapter;
import com.hilmi.githubusersearch.model.Following;
import com.hilmi.githubusersearch.network.ApiClient;
import com.hilmi.githubusersearch.view.activity.UserDetailActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {

    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LinearLayout emptyFollowingLayout = view.findViewById(R.id.layout_empty_following);
        emptyFollowingLayout.setVisibility(View.GONE);

        TextView tvEmptyFollowing = view.findViewById(R.id.tv_empty_following);
        tvEmptyFollowing.setText(String.format(getResources().getString(R.string.no_following), UserDetailActivity.sCurrentUser));

        final RecyclerView rvListFollowing = view.findViewById(R.id.rv_following);
        rvListFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        final ListFollowingAdapter followingAdapter = new ListFollowingAdapter();
        followingAdapter.notifyDataSetChanged();
        rvListFollowing.setAdapter(followingAdapter);

        followingAdapter.setOnItemClickCallback(new ListFollowingAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Following item) {
                showSelectedItem(item);
            }
        });

        Call<ArrayList<Following>> call = ApiClient.githubService().getListFollowing(UserDetailActivity.sCurrentUser);
        call.enqueue(new Callback<ArrayList<Following>>() {
            @Override
            public void onResponse(Call<ArrayList<Following>> call, Response<ArrayList<Following>> response) {
                ArrayList<Following> list = response.body();
                if (list.size() != 0) {
                    rvListFollowing.setVisibility(View.VISIBLE);
                    emptyFollowingLayout.setVisibility(View.GONE);
                    followingAdapter.setData(list);
                } else {
                    rvListFollowing.setVisibility(View.GONE);
                    emptyFollowingLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Following>> call, Throwable t) {
                Log.d(getTag(),t.getMessage());
            }
        });
    }

    private void showSelectedItem(Following item) {
        Intent intent = new Intent(getContext(), UserDetailActivity.class);
        intent.putExtra("USERNAME", item.getLogin());
        startActivity(intent);
    }
}

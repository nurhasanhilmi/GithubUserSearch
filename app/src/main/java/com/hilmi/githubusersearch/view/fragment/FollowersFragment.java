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
import com.hilmi.githubusersearch.adapter.ListFollowersAdapter;
import com.hilmi.githubusersearch.model.Followers;
import com.hilmi.githubusersearch.network.ApiClient;
import com.hilmi.githubusersearch.view.activity.UserDetailActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {

    public FollowersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LinearLayout emptyFollowersLayout = view.findViewById(R.id.layout_empty_followers);
        emptyFollowersLayout.setVisibility(View.GONE);

        TextView tvEmptyFollowers = view.findViewById(R.id.tv_empty_followers);
        tvEmptyFollowers.setText(String.format(getResources().getString(R.string.no_followers), UserDetailActivity.sCurrentUser));

        final RecyclerView rvListFollowers = view.findViewById(R.id.rv_followers);
        rvListFollowers.setLayoutManager(new LinearLayoutManager(getContext()));
        final ListFollowersAdapter followersAdapter = new ListFollowersAdapter();
        followersAdapter.notifyDataSetChanged();
        rvListFollowers.setAdapter(followersAdapter);

        followersAdapter.setOnItemClickCallback(new ListFollowersAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Followers item) {
                showSelectedItem(item);
            }
        });

        Call<ArrayList<Followers>> call = ApiClient.githubService().getListFollowers(UserDetailActivity.sCurrentUser);
        call.enqueue(new Callback<ArrayList<Followers>>() {
            @Override
            public void onResponse(Call<ArrayList<Followers>> call, Response<ArrayList<Followers>> response) {
                ArrayList<Followers> list = response.body();
                if (list.size() != 0) {
                    rvListFollowers.setVisibility(View.VISIBLE);
                    emptyFollowersLayout.setVisibility(View.GONE);
                    followersAdapter.setData(list);
                } else {
                    rvListFollowers.setVisibility(View.GONE);
                    emptyFollowersLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Followers>> call, Throwable t) {
                Log.d(getTag(),t.getMessage());
            }
        });
    }

    private void showSelectedItem(Followers item) {
        Intent intent = new Intent(getContext(), UserDetailActivity.class);
        intent.putExtra("USERNAME", item.getLogin());
        startActivity(intent);
    }
}

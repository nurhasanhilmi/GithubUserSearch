package com.hilmi.githubusersearch.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.hilmi.githubusersearch.R;
import com.hilmi.githubusersearch.adapter.SectionsPagerAdapter;
import com.hilmi.githubusersearch.model.User;
import com.hilmi.githubusersearch.network.ApiClient;
import com.hilmi.githubusersearch.viewmodel.UserFavoriteViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView ivAvatar;
    private TextView tvName, tvLogin, tvBio, tvCompany, tvLocation, tvWebsite;
    private Button btnFavorite;

    public static String sCurrentUser;

    private User user;

    private boolean isFavorite = false;

    private UserFavoriteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();
        sCurrentUser = intent.getStringExtra("USERNAME");

        viewModel = new ViewModelProvider(this).get(UserFavoriteViewModel.class);

        viewModel.getAllUser().observe(this, new Observer<List<com.hilmi.githubusersearch.db.User>>() {
            @Override
            public void onChanged(List<com.hilmi.githubusersearch.db.User> users) {
                if (users.size() != 0) {
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).username.equals(sCurrentUser)) {
                            isFavorite = true;
                            btnFavorite.setText(getResources().getString(R.string.unfavorite_text));
                            btnFavorite.setTextColor(getResources().getColor(R.color.colorSecondary));
                            btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
                            Log.d("DB Get Username", users.get(i).username);
                        }
                    }
                }
            }
        });

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_detail_user);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tab_detail_user);
        tabs.setupWithViewPager(viewPager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(sCurrentUser);
        }

        btnFavorite = findViewById(R.id.btn_favorite);
        btnFavorite.setOnClickListener(this);
        ivAvatar = findViewById(R.id.iv_avatar_detail);
        tvName = findViewById(R.id.tv_name_detail);
        tvLogin = findViewById(R.id.tv_login_detail);
        tvBio = findViewById(R.id.tv_bio_detail);
        tvCompany = findViewById(R.id.tv_company_detail);
        tvLocation = findViewById(R.id.tv_location_detail);
        tvWebsite = findViewById(R.id.tv_website);

        Call<User> call = ApiClient.githubService().getUser(sCurrentUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UserDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                } else {
                    user = response.body();
                    attachUserOnView(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("User Detail", "On Failure Call");
            }
        });
    }

    private void attachUserOnView(User user) {
        Glide.with(this)
                .load(user.getAvatarUrl())
                .apply(new RequestOptions().override(80, 80))
                .into(ivAvatar);

        tvName.setText(user.getName());
        tvLogin.setText(user.getLogin());
        tvBio.setText(user.getBio());
        tvCompany.setText(user.getCompany());
        tvLocation.setText(user.getLocation());
        tvWebsite.setText(user.getBlog());
        websiteOnClick(user.getBlog());
    }

    private void websiteOnClick(final String url) {
        tvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_favorite) {
            if (isFavorite) {
                setUserAsFavorite(false);
            } else {
                setUserAsFavorite(true);
            }
        }
    }

    private void setUserAsFavorite(boolean state) {
        if (state) {
            isFavorite = true;
            viewModel.insert(new com.hilmi.githubusersearch.db.User(user.getName(), user.getLogin(), user.getAvatarUrl()));
            btnFavorite.setText(getResources().getString(R.string.unfavorite_text));
            btnFavorite.setTextColor(getResources().getColor(R.color.colorSecondary));
            btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorPrimaryText));
        } else {
            isFavorite = false;
            viewModel.delete(sCurrentUser);
            btnFavorite.setText(getResources().getString(R.string.favorite_text));
            btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
            btnFavorite.setTextColor(getResources().getColor(R.color.colorPrimaryText));
        }
    }
}

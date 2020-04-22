package com.hilmi.githubusersearch.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hilmi.githubusersearch.R;
import com.hilmi.githubusersearch.adapter.UserFavoriteAdapter;
import com.hilmi.githubusersearch.db.User;
import com.hilmi.githubusersearch.viewmodel.UserFavoriteViewModel;

import java.util.List;

public class FavoriteUsersActivity extends AppCompatActivity {
    private UserFavoriteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_users);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Favorite User");
        }

        RecyclerView recyclerView = findViewById(R.id.rv_favorite);
        final UserFavoriteAdapter adapter = new UserFavoriteAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(UserFavoriteViewModel.class);
        viewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users.size() != 0) {
                    adapter.setUsers(users);
                }
                else {
                    Toast.makeText(FavoriteUsersActivity.this, "User Favorite Is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter.setOnItemClickCallback(new UserFavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(User item) {
                showSelectedItem(item);
            }
        });
    }

    private void showSelectedItem(User item) {
        Intent intent = new Intent(FavoriteUsersActivity.this, UserDetailActivity.class);
        intent.putExtra("USERNAME", item.username);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }
}

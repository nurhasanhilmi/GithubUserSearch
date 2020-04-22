package com.hilmi.githubusersearch.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hilmi.githubusersearch.R;
import com.hilmi.githubusersearch.viewmodel.SearchViewModel;
import com.hilmi.githubusersearch.adapter.SearchAdapter;
import com.hilmi.githubusersearch.model.SearchUserItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SearchView svSearch;
    private RecyclerView rvSearch;
    private static ProgressBar pbSearch;

    private SearchAdapter searchAdapter;

    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_action_icon);

        svSearch = findViewById(R.id.sv_search);
        rvSearch = findViewById(R.id.rv_search);
        pbSearch = findViewById(R.id.pb_Search);

        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter();
        searchAdapter.notifyDataSetChanged();
        rvSearch.setAdapter(searchAdapter);

        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);

        searchAdapter.setOnItemClickCallback(new SearchAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(SearchUserItem item) {
                showSelectedItem(item);
            }
        });

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showLoading(true);
                searchViewModel.setData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchViewModel.getData().observe(this, new Observer<ArrayList<SearchUserItem>>() {
            @Override
            public void onChanged(ArrayList<SearchUserItem> searchUserItems) {
                if (searchUserItems.size() != 0) {
                    searchAdapter.setData(searchUserItems);
                } else {
                    Toast.makeText(MainActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void showLoading(Boolean state) {
        if (state) {
            pbSearch.setVisibility(View.VISIBLE);
        } else {
            pbSearch.setVisibility(View.GONE);
        }
    }

    private void showSelectedItem(SearchUserItem item) {
        Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
        intent.putExtra("USERNAME", item.getLogin());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_favorite_user) {
            Intent i = new Intent(this, FavoriteUsersActivity.class);
            startActivity(i);
            return true;
        }
        return true;
    }
}

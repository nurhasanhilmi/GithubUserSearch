package com.hilmi.githubusersearch.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hilmi.githubusersearch.model.SearchUserItem;
import com.hilmi.githubusersearch.model.SearchUsers;
import com.hilmi.githubusersearch.network.ApiClient;
import com.hilmi.githubusersearch.view.activity.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ArrayList<SearchUserItem>> data = new MutableLiveData<>();

    public void setData(String query) {
        Call<SearchUsers> call = ApiClient.githubService().getSearchResult(query);
        call.enqueue(new Callback<SearchUsers>() {
            @Override
            public void onResponse(Call<SearchUsers> call, Response<SearchUsers> response) {
                if (!response.isSuccessful()) {
                    Log.d("On Response", response.message());
                }
                else if (response.body() != null) {
                    ArrayList<SearchUserItem> items = new ArrayList<>(response.body().getItems());
                    data.postValue(items);
                }
                MainActivity.showLoading(false);
            }

            @Override
            public void onFailure(Call<SearchUsers> call, Throwable t) {
                MainActivity.showLoading(false);
                Log.d("onFailure", t.getMessage());
            }
        });
    }
    public LiveData<ArrayList<SearchUserItem>> getData() {
        return data;
    }
}

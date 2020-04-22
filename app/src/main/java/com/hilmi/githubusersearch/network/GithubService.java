package com.hilmi.githubusersearch.network;

import com.hilmi.githubusersearch.model.Followers;
import com.hilmi.githubusersearch.model.Following;
import com.hilmi.githubusersearch.model.SearchUsers;
import com.hilmi.githubusersearch.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {
    @Headers("Authentication: token b9be49a4110b684fadb81c486c077a307736b019")
    @GET("search/users")
    Call<SearchUsers> getSearchResult(@Query("q") String query);

    @Headers("Authentication: token b9be49a4110b684fadb81c486c077a307736b019")
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @Headers("Authentication: token b9be49a4110b684fadb81c486c077a307736b019")
    @GET("users/{username}/followers")
    Call<ArrayList<Followers>> getListFollowers(@Path("username") String username);

    @Headers("Authentication: token b9be49a4110b684fadb81c486c077a307736b019")
    @GET("users/{username}/following")
    Call<ArrayList<Following>> getListFollowing(@Path("username") String username);
}

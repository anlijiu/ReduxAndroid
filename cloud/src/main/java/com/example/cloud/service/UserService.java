package com.example.cloud.service;

import com.example.cloud.models.User;
import com.example.cloud.models.UserList;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface UserService {

    @GET("/search/users?per_page=20")
    Observable<UserList> searchGithubUsers(@Query("q") String searchTerm, @Query("page") int page);

    @GET("/users/{username}")
    Observable<User> getUser(@Path("username") String username);

    @GET("users/{userName}/events")
    Observable<String> fetchGitEvents(@Path("userName") String userName, @QueryMap Map<String, String> requestParams);

    @GET("users/{userName}")
    Observable<String> validateUser(@Path("userName") String userName, @QueryMap Map<String, String> requestParams);

}
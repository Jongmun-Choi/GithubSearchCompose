package com.dave.githubsearchcompose.network

import com.dave.githubsearchcompose.model.Repository
import com.dave.githubsearchcompose.model.User
import com.dave.githubsearchcompose.model.UserSearchResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    suspend fun searchUser(@Query("q") query: String,
                           @Query("page") page: Int = 1,
                           @Query("per_page") perPage: Int = 20): Result<UserSearchResult>

    @GET("/users/{username}")
    suspend fun getUserProfile(@Path("username") username: String): Result<User>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): Result<List<Repository>>

    @GET("/user")
    suspend fun getMyProfile() : Result<User>

    @GET("/user/repos")
    suspend fun getMyRepository() : Result<List<Repository>>
}
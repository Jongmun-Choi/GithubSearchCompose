package com.dave.githubsearchcompose.network

import com.dave.githubsearchcompose.model.UserSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    suspend fun searchUser(@Query("q") query: String,
                           @Query("page") page: Int = 1,
                           @Query("per_page") perPage: Int = 20): Result<UserSearchResult>

}
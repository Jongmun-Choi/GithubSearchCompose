package com.dave.githubsearchcompose.repository

import com.dave.githubsearchcompose.network.ApiService
import com.dave.githubsearchcompose.network.NetworkModule
import jakarta.inject.Inject

class ApiRepository @Inject constructor(@NetworkModule.Api private val apiService: ApiService) {

    suspend fun getUserProfile(userName : String) = apiService.getUserProfile(userName)
    suspend fun getUserRepos(userName : String) = apiService.getUserRepos(userName)
    suspend fun getSearchUser(query : String, pageNumber: Int) = apiService.searchUser(query = query, page = pageNumber)
    suspend fun getMyProfile() = apiService.getMyProfile()
    suspend fun getMyRepository() = apiService.getMyRepository()

}
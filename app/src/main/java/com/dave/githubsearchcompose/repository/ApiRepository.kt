package com.dave.githubsearchcompose.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dave.githubsearchcompose.model.User
import com.dave.githubsearchcompose.network.ApiService
import com.dave.githubsearchcompose.network.NetworkModule
import com.dave.githubsearchcompose.view.userlist.paging.UserListPagingSource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ApiRepository @Inject constructor(@NetworkModule.Api private val apiService: ApiService) {

    suspend fun getUserProfile(userName: String) = apiService.getUserProfile(userName)
    suspend fun getUserRepos(userName: String) = apiService.getUserRepos(userName)
    fun getSearchUser(query: String): Flow<PagingData<User>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                UserListPagingSource(apiService, query)
            }).flow
    suspend fun getMyProfile() = apiService.getMyProfile()
    suspend fun getMyRepository() = apiService.getMyRepository()

}
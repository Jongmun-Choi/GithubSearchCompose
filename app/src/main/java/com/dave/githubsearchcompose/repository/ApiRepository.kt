package com.dave.githubsearchcompose.repository

import com.dave.githubsearchcompose.network.ApiService
import com.dave.githubsearchcompose.network.NetworkModule
import jakarta.inject.Inject

class ApiRepository @Inject constructor(@NetworkModule.Api private val apiService: ApiService) {

}
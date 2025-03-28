package com.dave.githubsearchcompose.repository

import com.dave.githubsearchcompose.network.AuthService
import com.dave.githubsearchcompose.network.NetworkModule
import jakarta.inject.Inject

class AuthRepository @Inject constructor(@NetworkModule.Auth private val authService: AuthService) {

    suspend fun getAccessToken(clientId:String, clientSecret: String, code:String) = authService.getAccessToken(clientId, clientSecret, code)

}
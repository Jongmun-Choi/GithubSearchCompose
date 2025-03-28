package com.dave.githubsearchcompose.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessToken(
    @field:Json(name="access_token")
    val accessToken: String
)

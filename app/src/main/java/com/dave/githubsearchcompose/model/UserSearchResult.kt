package com.dave.githubsearchcompose.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserSearchResult(
    @Json(name = "incomplete_results") val isFinish : Boolean,
    @Json(name = "items") val items: List<User>
)
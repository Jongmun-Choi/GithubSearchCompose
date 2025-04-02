package com.dave.githubsearchcompose.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Repository(
    @field:Json(name = "id")
    val id : Long,
    @field:Json(name = "name")
    val name : String,
    @field:Json(name = "private")
    val isPrivate : Boolean,
    @field:Json(name = "html_url")
    val url : String,
    @field:Json(name = "description")
    val description : String?
)
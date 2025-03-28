package com.dave.githubsearchcompose.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Objects

@JsonClass(generateAdapter = true)
data class User(

    @field:Json(name = "id")
    val id : Int,
    @field:Json(name = "login")
    val name : String,
    @field:Json(name = "avatar_url")
    val avatarUrl : String,
    @field:Json(name = "html_url")
    val repoUrl : String
) {

    override fun equals(other: Any?): Boolean {
        return other is User && other.id == id && other.name == name && other.avatarUrl == avatarUrl && other.repoUrl == repoUrl
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, avatarUrl, repoUrl)
    }
}
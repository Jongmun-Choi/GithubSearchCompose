package com.dave.githubsearchcompose.model

import androidx.compose.runtime.Composable

data class TabData(
    val name: String,
    val icon: Int,
    val screen: @Composable () -> Unit
)
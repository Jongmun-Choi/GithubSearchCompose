package com.dave.githubsearchcompose.view.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.dave.githubsearchcompose.ui.theme.GithubSearchComposeTheme
import com.dave.githubsearchcompose.viewmodel.UserViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: UserViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GithubSearchComposeTheme {
                TabScreen()
            }
        }
    }

}
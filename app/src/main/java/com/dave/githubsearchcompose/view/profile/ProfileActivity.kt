package com.dave.githubsearchcompose.view.profile

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.dave.githubsearchcompose.model.User
import com.dave.githubsearchcompose.viewmodel.UserViewModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlin.jvm.java

@AndroidEntryPoint
class ProfileActivity : ComponentActivity() {

    val userViewModel by viewModels<UserViewModel>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user = Moshi.Builder().build().adapter(User::class.java).fromJson(intent.getStringExtra("user")?:"")
            ProfileScreen(viewModel = userViewModel, user = user)
        }
    }
}
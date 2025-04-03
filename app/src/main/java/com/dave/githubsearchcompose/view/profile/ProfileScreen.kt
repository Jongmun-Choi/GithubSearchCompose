package com.dave.githubsearchcompose.view.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dave.githubsearchcompose.model.User
import com.dave.githubsearchcompose.viewmodel.UserViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@NonRestartableComposable
fun ProfileScreen(
    viewModel: UserViewModel,
    user : User?
) {
    val userData = viewModel.userProfile.collectAsStateWithLifecycle().value
    val repoList = viewModel.userRepos.collectAsStateWithLifecycle().value
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        user?.let {
            viewModel.getUserProfile(user.name)
            viewModel.getUserRepos(user.name)
        } ?: run {
            viewModel.getMyProfile()
            viewModel.getMyRepository()
        }

    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            GlideImage(model = userData.avatarUrl, modifier = Modifier.size(50.dp), contentDescription = "profile image")
            Column {
                Text(userData.name)
                Text(userData.repoUrl)
            }

        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState
        ) {
            items(repoList.size) { index ->
                RepositoryItem(repoList[index]) { }
            }
        }

    }

}
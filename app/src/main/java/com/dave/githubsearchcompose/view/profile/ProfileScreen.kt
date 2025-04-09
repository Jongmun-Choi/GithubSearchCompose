package com.dave.githubsearchcompose.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.dave.githubsearchcompose.model.User
import com.dave.githubsearchcompose.ui.theme.Gray
import com.dave.githubsearchcompose.view.component.UserItem
import com.dave.githubsearchcompose.viewmodel.UserViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@NonRestartableComposable
fun ProfileScreen(
    viewModel: UserViewModel,
    user : User?
) {
    val userData by viewModel.userProfile.collectAsStateWithLifecycle()
    val repoList by viewModel.userRepos.collectAsStateWithLifecycle()
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
        UserItem(userData, {})
        Column {
            Text(
                modifier = Modifier.background(color = Gray).fillMaxWidth().offset(x = 12.dp),
                text = "Repositories")
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {
                items(repoList.size) { index ->
                    RepositoryItem(repoList[index])
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }

    }

}
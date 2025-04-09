package com.dave.githubsearchcompose.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dave.githubsearchcompose.model.User
import com.dave.githubsearchcompose.ui.theme.Black

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserItem(userData: User, clickProcess: () -> Unit) {

    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp).clickable { clickProcess() }) {

        GlideImage(model = userData.avatarUrl, modifier = Modifier.size(50.dp).border(1.dp, color = Black, shape = CircleShape).clip(
            CircleShape
        ), contentDescription = "profile image")
        Column(modifier = Modifier.offset(x = 16.dp)) {
            Text(userData.name)
            Text(userData.repoUrl)
        }

    }

}
package com.dave.githubsearchcompose.view.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dave.githubsearchcompose.R
import com.dave.githubsearchcompose.model.Repository
import java.nio.file.WatchEvent

@Composable
fun RepositoryItem(
    repository: Repository,
    onItemClick: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
            .border(1.dp, Color.Gray)
            .clip(RoundedCornerShape(6.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.Start)
        ) {
            Text(repository.name)
            Row( modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                Image(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = if (repository.isPrivate) R.drawable.ic_lock else R.drawable.ic_unlock),
                    contentDescription = null)
            }
        }

        Text(repository.getRepositoryDescription())
        Text(repository.url)
    }

}
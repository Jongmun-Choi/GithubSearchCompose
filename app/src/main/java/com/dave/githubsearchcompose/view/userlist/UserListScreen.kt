package com.dave.githubsearchcompose.view.userlist

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.dave.githubsearchcompose.R
import com.dave.githubsearchcompose.enums.TextFieldState
import com.dave.githubsearchcompose.ui.theme.Gray
import com.dave.githubsearchcompose.ui.theme.grey_100
import com.dave.githubsearchcompose.view.component.FocusBorderEditText
import com.dave.githubsearchcompose.view.component.UserItem
import com.dave.githubsearchcompose.view.profile.ProfileActivity
import com.dave.githubsearchcompose.viewmodel.UserViewModel
import com.squareup.moshi.Moshi
import kotlin.jvm.java

@Composable
fun UserListScreen(viewModel: UserViewModel, context: Context) {

    var queryString by rememberSaveable { mutableStateOf("") }
    val userList = viewModel.userList.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    Column (modifier = Modifier.padding(16.dp, 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            FocusBorderEditText(
                value = queryString,
                onChangeText = { queryString = it },
                modifier = Modifier.weight(0.7f),
                placeHolder = R.string.search_hint,
                state = TextFieldState.ACTIVE,
            )
            IconButton(modifier = Modifier.fillMaxWidth()
                .offset(x = 8.dp)
                .height(52.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(grey_100),
                        onClick = {viewModel.getSearchUserList(queryString)}) {
                Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = null)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            if(userList.itemCount == 0) {
                item {
                    Text( color = Gray,
                        text = if(queryString.isEmpty()) stringResource(R.string.empty_query) else stringResource(R.string.empty_user_list))
                }
            }else {
                items(userList.itemCount) { index ->
                    UserItem(userData = userList[index] ?: return@items,
                        {
                            val intent = Intent(context, ProfileActivity::class.java)
                            val userJson = Moshi.Builder().build()
                                .adapter(com.dave.githubsearchcompose.model.User::class.java)
                                .toJson(userList[index])
                            intent.putExtra("user", userJson)
                            context.startActivity(intent)
                        })
                }
            }
        }
    }

}
package com.dave.githubsearchcompose.view.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.dave.githubsearchcompose.R
import com.dave.githubsearchcompose.model.TabData
import com.dave.githubsearchcompose.ui.theme.Purple40
import com.dave.githubsearchcompose.view.profile.ProfileScreen
import com.dave.githubsearchcompose.view.userlist.UserListScreen
import com.dave.githubsearchcompose.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(
    viewModel: UserViewModel
) {

    val pages = listOf(TabData("Search", R.drawable.ic_search, { UserListScreen(viewModel) }), TabData("Profile", R.drawable.ic_profile, { ProfileScreen(viewModel = viewModel, user = null) }))

    val pagerScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = { pages.size },
        initialPageOffsetFraction = 0f,
        initialPage = 0
    )
    var tabIndex = pagerState.currentPage

    Column {
        HorizontalPager(state = pagerState, userScrollEnabled = true, modifier = Modifier.fillMaxWidth().weight(1f), key = {pages[it].name}) { index ->
            pages[index].screen()
        }

        SecondaryTabRow (
            selectedTabIndex = tabIndex,
            modifier = Modifier.fillMaxWidth()
                .background(Purple40),
            containerColor = Purple40

        ) {
            pages.forEachIndexed { index, _ ->
                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        Log.i("TabScreen", "onClick")
                        pagerScope.launch {

                            Log.i("TabScreen", "launch")
                            pagerState.animateScrollToPage(index)
                        }
                    }

                ) {
                    Icon(painter = painterResource(pages[index].icon), contentDescription = null)
                    Text(text = pages[index].name)
                }

            }

        }

    }
}
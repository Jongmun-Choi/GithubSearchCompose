package com.dave.githubsearchcompose.view.userlist.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dave.githubsearchcompose.model.User
import com.dave.githubsearchcompose.model.UserSearchResult
import com.dave.githubsearchcompose.network.ApiService

class UserListPagingSource(private val service: ApiService, private val query: String): PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1
        return try {
            val response = service.searchUser(query, page, params.loadSize)
            LoadResult.Page(
                data = response.getOrDefault(UserSearchResult(isFinish = true, items = emptyList())).items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page + 1)
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
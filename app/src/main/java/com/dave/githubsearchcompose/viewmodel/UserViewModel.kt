package com.dave.githubsearchcompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dave.githubsearchcompose.model.Repository
import com.dave.githubsearchcompose.model.User
import com.dave.githubsearchcompose.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(application: Application, private val repository: ApiRepository) : AndroidViewModel(application) {

    private val _userProfile = MutableStateFlow(User(id = -1, name = "", avatarUrl = "", repoUrl = ""))
    val userProfile : StateFlow<User> = _userProfile.asStateFlow()

    private val _userRepos = MutableStateFlow<MutableList<Repository>>(mutableListOf())
    val userRepos : StateFlow<List<Repository>> = _userRepos.asStateFlow()

    private val _userList = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val userList : StateFlow<PagingData<User>> = _userList.asStateFlow()

    private var pageNumber = 1

    fun getUserProfile(userName : String) =
        viewModelScope.launch {
            repository.getUserProfile(userName)
                .onSuccess { userData ->
                    _userProfile.emit(userData)
                }
                .onFailure {
                    // 에러처리
                }
        }


    fun getUserRepos(userName : String) =
        viewModelScope.launch {
            repository.getUserRepos(userName)
                .onSuccess { repoData ->
                    val repoList = _userRepos.value.toMutableList()
                    repoList.addAll(repoData)
                    _userRepos.emit(repoList)
                }
                .onFailure {
                    // 에러처리
                }
        }


    fun getMyProfile() =
        viewModelScope.launch {
            repository.getMyProfile()
                .onSuccess { userData ->
                    _userProfile.emit(userData)
                }
                .onFailure {
                    // 에러처리
                }
        }


    fun getMyRepository() =
        viewModelScope.launch {
            repository.getMyRepository()
                .onSuccess { repoData ->
                    val repoList = _userRepos.value.toMutableList()
                    repoList.addAll(repoData)
                    _userRepos.emit(repoList)
                }
                .onFailure {
                    // 에러처리
                }
        }

    fun getSearchUserList(query : String, isReset : Boolean = false) = viewModelScope.launch {
        repository.getSearchUser(query)
            .cachedIn(viewModelScope)
            .collect { userList ->
                _userList.value = userList
            }
    }
}
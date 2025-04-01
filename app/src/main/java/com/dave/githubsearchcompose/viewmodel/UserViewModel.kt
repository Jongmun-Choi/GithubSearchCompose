package com.dave.githubsearchcompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dave.githubsearchcompose.GithubComposeApplication
import com.dave.githubsearchcompose.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(application: Application, repository: ApiRepository) : AndroidViewModel(application) {

}
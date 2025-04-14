package com.dave.githubsearchcompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dave.githubsearchcompose.BuildConfig
import com.dave.githubsearchcompose.enums.LoginState
import com.dave.githubsearchcompose.repository.AuthRepository
import com.dave.githubsearchcompose.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@HiltViewModel
class AuthViewModel @Inject constructor(application: Application, private val authRepository: AuthRepository, private val tokenRepository: TokenRepository) : AndroidViewModel(application = application) {

    private val _loginState = MutableStateFlow(LoginState.UNKNOWN)
    val loginState : StateFlow<LoginState> = _loginState.asStateFlow()

    fun checkLogin() {
        viewModelScope.launch {
            tokenRepository.getToken().collect { token ->
                _loginState.emit(if(token.isEmpty()) LoginState.LOGOUT else LoginState.LOGIN)
            }
        }
    }

    fun getAccessToken(code : String) {
        viewModelScope.launch {
            try {
                authRepository.getAccessToken(BuildConfig.clientId, BuildConfig.clientSecret, code)
                    .onSuccess { result ->
                        tokenRepository.saveToken(result.accessToken)
                        _loginState.emit(LoginState.LOGIN)
                    }
                    .onFailure {
                        // 에러 코드에 따른 처리 필요
                    }
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
            }
        }

    }

}
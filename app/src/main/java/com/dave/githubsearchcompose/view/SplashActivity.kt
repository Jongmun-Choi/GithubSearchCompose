package com.dave.githubsearchcompose.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import com.dave.githubsearchcompose.ui.theme.GithubSearchComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dave.githubsearchcompose.BuildConfig
import com.dave.githubsearchcompose.view.splash.SplashScreen
import com.dave.githubsearchcompose.view.userlist.UserListActivity
import com.dave.githubsearchcompose.viewmodel.AuthViewModel

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            GithubSearchComposeTheme {
                SplashScreen(
                    loginProcess = { loginProcess() },
                    viewModel = viewModel,
                    moveToUserList = {
                        startActivity(Intent(this, UserListActivity::class.java))
                        finish()
                    }
                )
            }
        }

        //Full Screen 처리
        val insetController = WindowCompat.getInsetsController(window, window.decorView)
        insetController.apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
        }

    }

    fun loginProcess() {
        val loginUrl = Uri.Builder().scheme("https").authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", BuildConfig.clientId)
            .build()

        CustomTabsIntent.Builder().build().also {
            it.launchUrl(this, loginUrl)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.getQueryParameter("code")?.let(viewModel::getAccessToken)
    }

}
package com.dave.githubsearchcompose.view.splash

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dave.githubsearchcompose.R
import com.dave.githubsearchcompose.enums.LoginState
import com.dave.githubsearchcompose.ui.theme.Black
import com.dave.githubsearchcompose.ui.theme.White
import com.dave.githubsearchcompose.viewmodel.AuthViewModel

@Composable
fun SplashScreen(
    loginProcess: () -> Unit,
    viewModel: AuthViewModel,
    moveToUserList: () -> Unit
) {

    val loginState : LoginState by viewModel.loginState.collectAsStateWithLifecycle()


    val alpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(loginState) {
        viewModel.checkLogin()
        when(loginState) {
            LoginState.LOGIN -> {
                moveToUserList()
            }
            else -> {}
        }
    }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(1500),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val painter = painterResource(R.drawable.github)

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.run {
                    width(200.dp)
                        .height(200.dp)
                        .alpha(alpha.value)
                },
                painter = painter,
                contentDescription = "LogoImage",
            )

            Spacer(modifier = Modifier.padding(top = 36.dp))

            Row(
                modifier = Modifier
                    .width(200.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .height(if (loginState == LoginState.LOGOUT) 36.dp else 0.dp)
                    .background(Black)
                    .animateContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.login_btn_text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Black)
                        .clickable(onClick = {
                            loginProcess()
                        }),
                    style = TextStyle(
                        color = White
                    ),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
        }

    }
}
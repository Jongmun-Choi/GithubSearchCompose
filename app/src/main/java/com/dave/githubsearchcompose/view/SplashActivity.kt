package com.dave.githubsearchcompose.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.dave.githubsearchcompose.ui.theme.GithubSearchComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dave.githubsearchcompose.R
import com.dave.githubsearchcompose.ui.theme.*

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            GithubSearchComposeTheme {
                SplashScreen()
            }
        }
        val insetController = WindowCompat.getInsetsController(window, window.decorView)
        insetController.apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
        }

    }

    @Preview
    @Composable
    fun SplashScreen() {
        val alpha = remember {
            Animatable(0f)
        }

        var isLogin by remember {
            mutableStateOf(false)
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
                        .height(if (isLogin) 36.dp else 0.dp)
                        .background(Black)
                        .animateContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = getString(R.string.login_btn_text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Black)
                            .clickable(onClick = {

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
}
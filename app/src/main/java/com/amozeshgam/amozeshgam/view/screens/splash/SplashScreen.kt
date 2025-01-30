package com.amozeshgam.amozeshgam.view.screens.splash

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.splash.SplashViewModel

@Preview(showBackground = true)
@Composable
fun ViewSplash(viewModel: SplashViewModel = hiltViewModel()) {

    val infiniteRotateAnimation = rememberInfiniteTransition(label = "rotate").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        ),
        label = ""
    )

    AmozeshgamTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AmozeshgamTheme.colors["background"]!!)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(R.drawable.ic_app_logo_without_border),
                contentDescription = null,
                tint = AmozeshgamTheme.colors["primary"]!!
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .rotate(infiniteRotateAnimation.value),
                painter = painterResource(R.drawable.ic_app_logo_border),
                contentDescription = null,
                tint = AmozeshgamTheme.colors["primary"]!!
            )
        }
    }
}


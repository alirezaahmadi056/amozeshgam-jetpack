package com.amozeshgam.amozeshgam.view.screens.splash

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.NavigationClusterHandler
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.ScreenName
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.splash.SplashViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.system.exitProcess

@Composable
fun ViewSplash(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {
    val showDialog = viewModel.showErrorDialog.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
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
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val registerForActivity =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result.all {
                    it.value
                }) {
                viewModel.onStartUp()
                viewModel.checkVersionAndWitchActivityToGo()
            } else {
                Toast.makeText(
                    context,
                    "شما دسترسی های لازم را ندادید و از برخی خدمات محروم می شوید",
                    Toast.LENGTH_LONG
                ).show()

                Toast.makeText(
                    context,
                    "برای ورود به اپلیکیشن دسترسی های لازم را فعال کنید",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    val systemController = rememberSystemUiController()
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
        if (showDialog.value) {
            UiHandler.ErrorDialog(
                imageId = R.drawable.ic_network_error,
                text = "لطفا دسترسی به اینترنت را بررسی کنید"
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = AmozeshgamTheme.colors["primary"]!!
                    )
                } else {
                    Text(
                        modifier = Modifier.clickable {
                            if (!isLoading.value) {
                                viewModel.checkVersionAndWitchActivityToGo()
                            }
                        },
                        text = "تلاش مجدد",
                        color = AmozeshgamTheme.colors["primary"]!!
                    )
                }
                Text(
                    modifier = Modifier.clickable {
                        exitProcess(0)
                    },
                    text = "خروج از برنامه",
                    color = AmozeshgamTheme.colors["errorColor"]!!
                )
            }
        }

        LaunchedEffect(Unit) {
            viewModel.checkPermission()
            viewModel.isAllPermissionGranted.observe(lifecycle) {
                if (it) {
                    viewModel.onStartUp()
                    viewModel.checkVersionAndWitchActivityToGo()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        registerForActivity.launch(
                            arrayOf(
                                android.Manifest.permission.POST_NOTIFICATIONS,
                                android.Manifest.permission.READ_SMS,
                                android.Manifest.permission.RECEIVE_SMS
                            )
                        )
                    } else {
                        registerForActivity.launch(
                            arrayOf(
                                android.Manifest.permission.READ_SMS,
                                android.Manifest.permission.RECEIVE_SMS
                            )
                        )
                    }
                }
            }
        }
        viewModel.whichActivityToGo.observe(lifecycle) { result ->
            when (result) {
                ScreenName.LOGIN -> {
                    navController.navigate(NavigationClusterHandler.Login.route) {
                        popUpTo(NavigationClusterHandler.Splash.route) { inclusive = true }
                    }
                }

                ScreenName.HOME -> {
                    navController.navigate(NavigationClusterHandler.Home.route) {
                        popUpTo(NavigationClusterHandler.Splash.route) { inclusive = true }
                    }
                }

                ScreenName.TOUR -> {
                    navController.navigate(NavigationScreenHandler.TourScreen.route) {
                        popUpTo(NavigationClusterHandler.Splash.route) { inclusive = true }
                    }
                }

                else -> Unit
            }
        }
    }
}



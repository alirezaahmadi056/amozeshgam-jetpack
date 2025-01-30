package com.amozeshgam.amozeshgam.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.view.screens.login.ViewLoginOne
import com.amozeshgam.amozeshgam.view.screens.login.ViewLoginThree
import com.amozeshgam.amozeshgam.view.screens.login.ViewLoginTwo
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.finishActivity.observe(this) {
            if (it) {
                finish()
            }
        }
        setContent {
            ViewLoginContent()
        }
    }
}

@Composable
fun ViewLoginContent() {
    AmozeshgamTheme {
        val navController = rememberNavController()
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .background(AmozeshgamTheme.colors["background"]!!),
            navController = navController,
            startDestination = NavigationHandler.LoginOneScreen.route,

            )
        {
            composable(
                route = NavigationHandler.LoginOneScreen.route,
            ) {
                ViewLoginOne(navController = navController)
            }
            composable(
                route = "${NavigationHandler.LoginTwoScreen.route}/{phone}",
                arguments = listOf(navArgument("phone") { type = NavType.StringType })
            ) {
                ViewLoginTwo(navController = navController)

            }
            composable(route = NavigationHandler.LoginThreeScreen.route) {
                ViewLoginThree()
            }
        }
    }
}





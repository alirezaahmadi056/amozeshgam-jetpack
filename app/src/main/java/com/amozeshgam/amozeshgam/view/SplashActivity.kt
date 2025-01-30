package com.amozeshgam.amozeshgam.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.handler.ActivityName
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.screens.splash.ViewSplash
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private val registerForActivity =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result.all {
                    it.value
                }) {
                viewModel.onStartUp()
                viewModel.checkVersionAndWitchActivityToGo()
            } else {
                Toast.makeText(
                    this,
                    "شما دسترسی های لازم را ندادید و از برخی خدمات محروم می شوید",
                    Toast.LENGTH_LONG
                ).show()

                Toast.makeText(
                    this,
                    "برای ورود به اپلیکیشن دسترسی های لازم را فعال کنید",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.checkPermission()
        viewModel.isAllPermissionGranted.observe(this) {
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
                }
            }
        }
        viewModel.witchActivityToGo.observe(this) { result ->
            if (intent.action == Intent.ACTION_SEND && intent.type == "text/plain") {
                GlobalUiModel.textProviderData.value =
                    intent.getStringExtra(Intent.EXTRA_TEXT).toString()
            }
            when (result) {
                ActivityName.LoginActivity -> startActivity(Intent(this, LoginActivity::class.java))
                ActivityName.HomeActivity -> {
                    val homeActivityIntent = Intent(this, HomeActivity::class.java)
                    if (
                        intent.action == Intent.ACTION_VIEW
                    ) {
                        homeActivityIntent.putExtra(
                            "deep-link",
                            viewModel.deepLinkHandler.whichActivityToGo(intent.data.toString())
                        )
                    }
                    startActivity(Intent(this, HomeActivity::class.java))
                }

                ActivityName.TourActivity -> startActivity(Intent(this, TourActivity::class.java))
                else -> Unit
            }
            finish()
        }
        setContent {
            val showDialog = viewModel.showErrorDialog.collectAsState()
            val isLoading = viewModel.isLoading.collectAsState()
            viewModel.hideStatusBar(window)
            AmozeshgamTheme(
                darkTheme = UiHandler.themeState()
            ) {
                ViewSplash()
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
                                finish()
                            },
                            text = "خروج از برنامه",
                            color = AmozeshgamTheme.colors["errorColor"]!!
                        )
                    }
                }
            }
        }
    }
}



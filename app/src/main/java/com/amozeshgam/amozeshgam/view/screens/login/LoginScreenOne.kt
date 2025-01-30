package com.amozeshgam.amozeshgam.view.screens.login

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.handler.RemoteStateHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.login.LoginViewModel

@OptIn(ExperimentalMotionApi::class)
@Composable
fun ViewLoginOne(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val phoneNumber = rememberSaveable {
        mutableStateOf("")
    }
    val buttonIsLoading = remember {
        mutableStateOf(false)
    }
    val lifecycle = LocalLifecycleOwner.current
    val showErrorDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val motionLayoutScene =
        context.resources.openRawResource(R.raw.animation_login_pages).readBytes().decodeToString()
    val scrollState = rememberScrollState()
    val errorDialogButtonIsLoading = remember {
        mutableStateOf(false)
    }
    val keyboardOpened = remember {
        mutableStateOf(false)
    }
    AmozeshgamTheme(
        darkTheme = UiHandler.themeState()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(AmozeshgamTheme.colors["background"]!!)
                .verticalScroll(scrollState),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                MotionLayout(
                    motionScene = MotionScene(content = motionLayoutScene),
                    progress = if (keyboardOpened.value) if (scrollState.value != 0) ((scrollState.value * 100) / scrollState.maxValue).toFloat() else scrollState.value.toFloat() else 0f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .layoutId("loginBackground"),
                        contentScale = ContentScale.FillWidth,
                        painter = painterResource(id = AmozeshgamTheme.assets["bgLogin"]!!),
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .layoutId("amozeshgamLogo"),
                        painter = painterResource(id = AmozeshgamTheme.assets["amozeshgamBanner"]!!),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                text = "ورود یا ثبت نام",
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_bold)),
                fontSize = 25.sp,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                text = ".لطفا شماره موبایل خود را وارد کنید",
                textAlign = TextAlign.End,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                fontSize = 15.sp,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
            UiHandler.OutLineTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(10.dp),
                label = {
                    Text(
                        text = "شماره موبایل",
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                    )
                },
                value = phoneNumber.value,
                onValueChange = {
                    if (it.length <= 11) {
                        phoneNumber.value = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon =
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (GlobalUiModel.textProviderData.value?.length == 11 && GlobalUiModel.textProviderData.value?.isDigitsOnly() == true) {
                            Image(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(20.dp)
                                    .clickable {
                                        phoneNumber.value = GlobalUiModel.textProviderData.value!!
                                    },
                                painter = painterResource(id = R.drawable.ic_lamp),
                                contentDescription = null,
                            )
                        }
                        if (phoneNumber.value.isNotBlank()) {
                            Icon(
                                modifier = Modifier.clickable {
                                    phoneNumber.value = ""
                                },
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = null,
                                tint = AmozeshgamTheme.colors["textColor"]!!
                            )
                        }

                    }

                }
            )
            Button(
                enabled = viewModel.phoneNumberValidating(phoneNumber.value),
                onClick = {
                    if (!buttonIsLoading.value) {
                        buttonIsLoading.value = true
                        viewModel.sendCode(phoneNumber.value)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmozeshgamTheme.colors["primary"]!!,
                    disabledContainerColor = AmozeshgamTheme.colors["disableContainer"]!!,
                ),
                shape = RoundedCornerShape(10.dp),
            ) {
                if (!buttonIsLoading.value) {
                    Text(
                        text = "تایید",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = ".آموزشگام را مطالعه کردم و می پذیرم",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                )
                Text(
                    modifier = Modifier.clickable { },
                    text = "شرایط و قوانین",
                    color = AmozeshgamTheme.colors["textButtonColor"]!!,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
            LaunchedEffect(Unit) {
                viewModel.codeSending.observe(lifecycle) {
                    buttonIsLoading.value = false
                    errorDialogButtonIsLoading.value = false
                    when (it) {
                        RemoteStateHandler.OK -> navController.navigate("${NavigationHandler.LoginTwoScreen.route}/${phoneNumber.value}")
                        RemoteStateHandler.BADRESPONSE -> showErrorDialog.value = true
                        RemoteStateHandler.ERROR -> showErrorDialog.value = true
                        else -> Unit
                    }

                }
            }
        }
        if (showErrorDialog.value) {
            UiHandler.ErrorDialog(
                onDismiss = {
                    showErrorDialog.value = false
                },
                imageId = R.drawable.ic_network_error,
                text = "لطفا دسترسی به اینترنت را بررسی کنید"
            ) {
                if (errorDialogButtonIsLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = AmozeshgamTheme.colors["primary"]!!
                    )
                } else {
                    Text(modifier = Modifier.clickable {
                        if (!errorDialogButtonIsLoading.value) {
                            errorDialogButtonIsLoading.value = true
                            viewModel.sendCode(phoneNumber.value)
                        }

                    }, text = "تلاش مجدد", color = AmozeshgamTheme.colors["primary"]!!)
                }
                Text(modifier = Modifier.clickable {
                    context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                }, text = "رفتن به تنظیمات", color = AmozeshgamTheme.colors["primary"]!!)
            }
        }
        UiHandler.KeyboardIsOpened(keyboardOpened = keyboardOpened)
    }
}


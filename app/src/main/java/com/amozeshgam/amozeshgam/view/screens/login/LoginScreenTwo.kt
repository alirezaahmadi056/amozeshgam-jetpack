package com.amozeshgam.amozeshgam.view.screens.login

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastJoinToString
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.handler.ValidatingStateHandler
import com.amozeshgam.amozeshgam.view.HomeActivity
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.login.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMotionApi::class)
@SuppressLint("HardwareIds")
@Composable
fun ViewLoginTwo(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val textFieldBoxColor = remember {
        mutableStateOf(false)
    }
    val textTimer = remember {
        mutableStateOf("2:00 ثانیه تا ارسال مجدد")
    }
    val focusRequester = viewModel.getTextCodeFocused()

    val code = remember {
        mutableStateListOf<String>()
    }
    val showErrorDialog = remember {
        mutableStateOf(false)
    }
    val lifecycle = LocalLifecycleOwner.current
    val time = remember {
        mutableIntStateOf(120)
    }
    val context = LocalContext.current
    val buttonIsLoading = remember {
        mutableStateOf(false)
    }
    val motionLayoutScene =
        context.resources.openRawResource(R.raw.animation_login_pages).readBytes().decodeToString()
    val scrollState = rememberScrollState()
    val phone = remember {
        navController.currentBackStackEntry?.arguments?.getString("phone") ?: ""
    }
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(AmozeshgamTheme.colors["background"]!!)

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
                        .wrapContentHeight()
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
                text = ".کد تایید ۵ رقمی را وارد کنید",
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
                text = "کد تایید برای شماره موبایل $phone ارسال شد",
                fontFamily = FontFamily(Font(R.font.yekan_bakh_bold)),
                textAlign = TextAlign.End,
                fontSize = 15.sp,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
            LazyRow(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .border(
                        2.dp,
                        if (textFieldBoxColor.value) AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["borderColor"]!!,
                        RoundedCornerShape(12.dp)
                    )
                    .background(AmozeshgamTheme.colors["background"]!!),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                items(focusRequester.size) { index ->
                    TextField(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .width(45.dp)
                            .focusRequester(focusRequester[index])
                            .onFocusEvent {
                                textFieldBoxColor.value = it.isFocused
                            }
                            .height(60.dp),
                        value = code.getOrElse(index) {
                            ""
                        },
                        onValueChange = { newValue ->
                            if (newValue.toIntOrNull() != null && code.getOrElse(index) { "" }
                                    .isEmpty()) {
                                if (code.getOrNull(index) != null) {
                                    code.removeAt(index)
                                }
                                val newIndex =
                                    if (index + 1 != focusRequester.size) index + 1 else index
                                focusRequester[newIndex].requestFocus()
                                code.add(index, newValue)
                            } else {
                                val newIndex =
                                    if (index != 0) index - 1 else 0
                                focusRequester[newIndex].requestFocus()
                                if (code.getOrNull(index) != null) {
                                    code[index] = ""
                                }
                            }
                        },
                        textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = AmozeshgamTheme.colors["borderColor"]!!,
                            focusedIndicatorColor = AmozeshgamTheme.colors["primary"]!!,
                            errorIndicatorColor = AmozeshgamTheme.colors["errorColor"]!!,
                            unfocusedContainerColor = AmozeshgamTheme.colors["background"]!!,
                            focusedContainerColor = AmozeshgamTheme.colors["background"]!!,
                            focusedTextColor = AmozeshgamTheme.colors["textColor"]!!,
                            unfocusedTextColor = AmozeshgamTheme.colors["textColor"]!!
                        )

                    )
                }
            }

            Button(
                onClick = {
                    if (!buttonIsLoading.value) {
                        if (time.intValue != 0) {
                            buttonIsLoading.value = true
                            viewModel.checkCode(
                                phone,
                                code.fastJoinToString(""),
                                viewModel.deviceHandler.getDeviceName(),
                                viewModel.deviceHandler.getAndroidId()
                            )


                        } else {
                            Toast.makeText(context, "کد شما منقضی شده است", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                enabled = code.size == 5,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmozeshgamTheme.colors["primary"]!!,
                    disabledContainerColor = AmozeshgamTheme.colors["disableContainer"]!!
                )
            ) {
                if (!buttonIsLoading.value) {
                    Text(
                        text = "تایید و ادامه",
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                        color = Color.White
                    )
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
                Text(
                    modifier = Modifier
                        .clickable {
                            if (textTimer.value == "ارسال مجدد") {
                                viewModel.sendCode(
                                    phone
                                )
                                time.intValue = 120
                            }
                        }
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    text = textTimer.value,
                    color = if (textTimer.value == "ارسال مجدد") AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                )
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
                                viewModel.checkCode(
                                    phone,
                                    code.fastJoinToString(""),
                                    viewModel.deviceHandler.getDeviceName(),
                                    viewModel.deviceHandler.getAndroidId()
                                )
                            }

                        }, text = "تلاش مجدد", color = AmozeshgamTheme.colors["primary"]!!)
                    }
                    Text(modifier = Modifier.clickable {
                        context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                    }, text = "رفتن به تنظیمات", color = AmozeshgamTheme.colors["primary"]!!)
                }
            }
            LaunchedEffect(time.intValue) {
                this.launch {
                    while (time.intValue > 0) {
                        delay(1000)
                        time.intValue -= 1
                        textTimer.value =
                            "${time.intValue / 60}:${time.intValue % 60} ثانیه تا ارسال مجدد"
                    }
                    textTimer.value = "ارسال مجدد"
                }
            }
            LaunchedEffect(Unit) {
                viewModel.codeIsValid.observe(lifecycle) {
                    buttonIsLoading.value = false
                    errorDialogButtonIsLoading.value = false
                    when (it.first) {
                        ValidatingStateHandler.VALID -> {
                            if (!it.second) {
                                navController.navigate(NavigationHandler.LoginThreeScreen.route)
                            } else {
                                context.startActivity(Intent(context, HomeActivity::class.java))
                                viewModel.finishActivity()
                            }
                        }

                        ValidatingStateHandler.INVALID -> {
                            showErrorDialog.value = true
                        }

                        ValidatingStateHandler.ERROR -> {
                            showErrorDialog.value = true
                        }

                        else -> Unit
                    }
                }
            }
            LaunchedEffect(Unit) {
                viewModel.startBrodCastReceiverAndGetCode(code)
            }
        }
        UiHandler.KeyboardIsOpened(keyboardOpened = keyboardOpened)
    }
}

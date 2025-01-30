package com.amozeshgam.amozeshgam.view.screens.home.profile

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.RemoteStateHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.EditProfileViewModel
import info.alirezaahmadi.persian_date_picker.controller.OnDatePickerEvents
import info.alirezaahmadi.persian_date_picker.view.PersianDatePickerDialog

@SuppressLint("Recycle")
@Composable
fun ViewEditProfile(
    viewModel: EditProfileViewModel = hiltViewModel(),
    navController: NavController,
) {
    val showDatePickerDialog = remember {
        mutableStateOf(false)
    }
    val name = remember {
        mutableStateOf("")
    }
    val phone = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val navEmail = remember {
        navController.currentBackStackEntry?.arguments?.getString("email") ?: ""
    }
    val navAvatar = remember {
        Uri.decode(navController.currentBackStackEntry?.arguments?.getString("avatar") ?: "")
    }
    val navName = remember {
        navController.currentBackStackEntry?.arguments?.getString("username") ?: ""
    }
    val navDate = remember {
        navController.currentBackStackEntry?.arguments?.getString("birthday") ?: ""
    }
    val date = remember {
        mutableStateOf("")
    }
    val showVerificationEmailDialog = remember {
        mutableStateOf(false)
    }
    val showErrorDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val updateUserIsLoading = remember {
        mutableStateOf(
            false
        )
    }
    val errorDialogIsLoading = remember {
        mutableStateOf(false)
    }
    val uploadAvatarIsLoading = remember {
        mutableStateOf(false)
    }
    val lifecycle = LocalLifecycleOwner.current
    val registerActivityForResult = LocalActivityResultRegistryOwner.current
    AmozeshgamTheme {
        UiHandler.ContentWithScaffold(
            title = "ویرایش اطلاعات کاربری",
            onBackButtonClick = {
                navController.popBackStack()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AmozeshgamTheme.colors["background"]!!)
                    .padding(10.dp)
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    AsyncImage(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(120.dp)
                            .clip(
                                CircleShape
                            ),
                        model = navAvatar,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(28.dp)
                            .clip(
                                CircleShape
                            )
                            .background(AmozeshgamTheme.colors["primary"]!!)
                            .padding(5.dp)
                            .clickable {
                                registerActivityForResult!!.activityResultRegistry
                                    .register(
                                        "avatar",
                                        ActivityResultContracts.PickVisualMedia()
                                    ) { uri: Uri? ->
                                        if (uri != null) {
                                            viewModel.uploadAvatar(uri)
                                        } else {
                                            Toast
                                                .makeText(context, "معتبر نیست", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                                    .launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                    ) {
                        if (uploadAvatarIsLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.ic_camera),
                                contentDescription = null
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "نام و نام خانوادگی",
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_thin)),
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
                Spacer(modifier = Modifier.height(15.dp))
                UiHandler.CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            width = 1.dp,
                            color = AmozeshgamTheme.colors["borderColor"]!!,
                            shape = RoundedCornerShape(10.dp),
                        ), value = name.value, onValueChange = {
                        name.value = it
                    }, shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "شماره تلفن",
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_thin)),
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
                Spacer(modifier = Modifier.height(15.dp))
                UiHandler.CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            width = 1.dp,
                            color = AmozeshgamTheme.colors["borderColor"]!!,
                            shape = RoundedCornerShape(10.dp),
                        ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    value = phone.value,
                    onValueChange = {
                        if (phone.value.length != 11) {
                            phone.value = it
                        }
                    },
                    readOnly = true
                )
                if (navEmail != "") {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = "ایمیل",
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_thin)),
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    UiHandler.CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(
                                width = 1.dp,
                                color = AmozeshgamTheme.colors["borderColor"]!!,
                                shape = RoundedCornerShape(10.dp),
                            ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        value = email.value,
                        onValueChange = {
                            email.value = it
                        },
                        readOnly = true
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "تاریخ تولد",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_thin))
                )
                Spacer(modifier = Modifier.height(15.dp))
                UiHandler.CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            width = 1.dp,
                            color = AmozeshgamTheme.colors["borderColor"]!!,
                            shape = RoundedCornerShape(10.dp),
                        ),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(modifier = Modifier.size(30.dp), onClick = {
                            showDatePickerDialog.value = true
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_calendar),
                                contentDescription = null
                            )
                        }
                    },
                    value = date.value,
                    onValueChange = {
                        date.value = it
                    },
                )
                Spacer(modifier = Modifier.height(15.dp))
                if (navEmail == "") {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = {
                            viewModel.requestForLoginWithEmail()
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = AmozeshgamTheme.colors["borderColor"]!!,
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AmozeshgamTheme.colors["background"]!!
                        )
                    ) {
                        Text(
                            "اضافه کردن اضافه کردن حساب گوگل",
                            fontFamily = AmozeshgamTheme.fonts["regular"],
                            color = AmozeshgamTheme.colors["textColor"]!!
                        )
                        Image(
                            modifier = Modifier.padding(5.dp),
                            painter = painterResource(R.drawable.ic_google),
                            contentDescription = null
                        )
                    }
                }
                AnimatedVisibility(visible = name.value != navName || email.value != navEmail || date.value != navDate) {
                    Button(modifier = Modifier.padding(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["primary"]!!),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            if (!updateUserIsLoading.value) {
                                if (name.value.isNotEmpty()) {
                                    viewModel.updateProfile(name = name.value, date = date.value)
                                    updateUserIsLoading.value = true
                                } else {
                                    Toast.makeText(
                                        context,
                                        "مقادیر خواسته شده را وارد نمایید",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }) {
                        if (updateUserIsLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White
                            )
                        } else {
                            Text(text = "اعمال تغییرات", color = Color.White)
                        }
                    }
                }

            }
            if (showDatePickerDialog.value) {
                PersianDatePickerDialog(onDismissRequest = {
                    showDatePickerDialog.value = false
                }, controller = object : OnDatePickerEvents {
                    override fun onConfirmButtonClick(year: Int, month: Int, day: Int) {
                        date.value = "$year/$month/$day"
                        showDatePickerDialog.value = false
                    }

                    override fun onClose() {
                        showDatePickerDialog.value = false
                    }
                })
            }
        }
        if (showVerificationEmailDialog.value) {
            UiHandler.CustomDialog {

            }
        }
        if (showErrorDialog.value) {
            UiHandler.ErrorDialog(
                imageId = R.drawable.ic_network_error,
                text = ".لطفا دسترسی به انترنت را بررسی کنید"
            ) {
                if (errorDialogIsLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = AmozeshgamTheme.colors["primary"]!!
                    )
                } else {
                    Text(
                        modifier = Modifier.clickable {
                            if (!errorDialogIsLoading.value) {
                                viewModel.updateProfile(name = name.value, date = date.value)
                                errorDialogIsLoading.value = true
                            }
                        },
                        text = "تلاش مجدد",
                        color = AmozeshgamTheme.colors["primary"]!!
                    )
                }
                Text(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    },
                    text = "خروج",
                    color = AmozeshgamTheme.colors["errorColor"]!!
                )
            }
        }
        LaunchedEffect(Unit) {
            viewModel.updateUser.observe(lifecycle) {
                when (it) {
                    RemoteStateHandler.OK -> {
                        Toast.makeText(context, "ثبت شد", Toast.LENGTH_SHORT).show()
                        updateUserIsLoading.value = false
                        showErrorDialog.value = false
                        errorDialogIsLoading.value = false
                        navController.popBackStack()
                    }

                    RemoteStateHandler.ERROR -> {
                        updateUserIsLoading.value = false
                        errorDialogIsLoading.value = false
                        showErrorDialog.value = true
                    }

                    else -> Unit
                }
            }
        }
        LaunchedEffect(Unit) {
            viewModel.emailCredentialResult.observe(lifecycle) {
                when (it) {
                    RemoteStateHandler.OK -> {
                        Toast.makeText(context, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }

                    else -> {
                        Toast.makeText(context, "خطا در ثبت ایمیل", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            viewModel.avatarUploaded.observe(lifecycle) {
                when (it) {
                    RemoteStateHandler.OK -> {
                        uploadAvatarIsLoading.value = false
                        Toast.makeText(context, "ما موفقیت ثبت شد", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }

                    RemoteStateHandler.LOADING -> {
                        uploadAvatarIsLoading.value = true
                    }

                    RemoteStateHandler.ERROR -> {
                        uploadAvatarIsLoading.value = false
                        Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }
        SideEffect {
            name.value = navName
            phone.value = navController.currentBackStackEntry?.arguments?.getString("phone") ?: ""
            email.value = navEmail
            date.value = navDate
        }
    }
}
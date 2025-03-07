package com.amozeshgam.amozeshgam.view.screens.home.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTicketSubjects
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.TicketViewModel

@Composable
fun ViewTicket(viewModel: TicketViewModel = hiltViewModel(), navController: NavController) {
    val subjectValue = remember {
        mutableStateOf<String>("")
    }
    val subjectOptions = remember {
        mutableStateOf<ApiResponseGetTicketSubjects?>(null)
    }
    val priorityValue = remember {
        mutableStateOf<String>("")
    }
    val priorityOptions = remember {
        mutableStateListOf<String>("بالا", "متوسط", "پایین")
    }
    val text = remember {
        mutableStateOf("")
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val buttonIsLoading = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = subjectOptions.value != null,
        worker = {
            subjectOptions.value = viewModel.getTicketSubjects().await()
            isLoading.value = false
            true
        }
    ) {
        AmozeshgamTheme {
            UiHandler.ContentWithScaffold(
                title = "درخواست پشتیبانی",
                onBackButtonClick = {
                    navController.popBackStack()
                }) {
                Column(
                    modifier = Modifier
                        .background(AmozeshgamTheme.colors["background"]!!)
                        .padding(20.dp)
                        .fillMaxSize()
                        .padding(it)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "موضوع درخواست",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        UiHandler.DropDownMenuOption(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    AmozeshgamTheme.colors["borderColor"]!!,
                                    RoundedCornerShape(7.dp)
                                ),
                            items = subjectOptions.value!!.subjects,
                            value = subjectValue.value,
                            hint = "موضوع"
                        ) { index ->
                            subjectValue.value = subjectOptions.value!!.subjects[index]
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "اولویت",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        UiHandler.DropDownMenuOption(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    AmozeshgamTheme.colors["borderColor"]!!,
                                    RoundedCornerShape(7.dp)
                                ),
                            items = priorityOptions,
                            value = priorityValue.value,
                            hint = "اولویت"
                        ) { index ->
                            priorityValue.value = priorityOptions[index]
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "متن درخواست",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    UiHandler.CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                            .border(
                                3.dp,
                                color = AmozeshgamTheme.colors["borderColor"]!!,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        value = text.value,
                        onValueChange = { text.value = it },
                        placeholder = "درخواست خود را بنویسید"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(10.dp)
                                .weight(1f)
                                .height(50.dp),
                            onClick = {
                                if (!buttonIsLoading.value && subjectValue.value.isNotEmpty() && priorityValue.value.isNotEmpty() && text.value.isNotEmpty()) {
                                    buttonIsLoading.value = true
                                    viewModel.registerRequest(
                                        text = text.value,
                                        priority = priorityValue.value,
                                        subject = subjectValue.value
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        "مقادیر را وارد کرده و صبر کنید",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AmozeshgamTheme.colors["primary"]!!
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            if (buttonIsLoading.value) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White
                                )
                            } else {
                                Text(
                                    text = "ارسال درخواست",
                                    color = Color.White,
                                    fontFamily = AmozeshgamTheme.fonts["regular"]
                                )
                            }
                        }
                        Button(
                            modifier = Modifier
                                .padding(10.dp)
                                .weight(1f)
                                .height(50.dp),
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["background"]!!),
                            border = BorderStroke(
                                1.dp,
                                color = AmozeshgamTheme.colors["primary"]!!
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "انصراف",
                                fontFamily = AmozeshgamTheme.fonts["regular"],
                                color = AmozeshgamTheme.colors["primary"]!!
                            )
                        }
                    }
                    LaunchedEffect(Unit) {
                        viewModel.registerRequest.observe(lifecycle) {
                            buttonIsLoading.value = false
                            if (it) {
                                Toast.makeText(context, "با موفقیت ثبت شد", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context, "خطا در ثبت درخواست", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.amozeshgam.amozeshgam.view.screens.home.roadMap.field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.roadMap.FieldViewModel

@Composable
fun ViewSingleMySubField(
    navController: NavController,
    viewModel: FieldViewModel = hiltViewModel()
) {
    val isLoading = remember {
        mutableStateOf(false)
    }
    val percent = remember {
        mutableFloatStateOf(0f)
    }
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        worker = {
            isLoading.value = false
            true
        }, onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.aspectRatio(16f / 9f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "برنامه نویسی اندروید",
                    fontSize = 25.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                AsyncImage(model = null, contentDescription = null)
            }
            if (true) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, color = AmozeshgamTheme.colors["primary"]!!),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["background"]!!)
                ) {
                    Text(
                        text = "مشاهده ی دوره ی پیشنهادی",
                        color = AmozeshgamTheme.colors["primary"]!!
                    )
                }
            }
            UiHandler.ViewTabAndPager(modifier = Modifier.fillMaxSize(), tabs = emptyArray()) {
                when (it) {
                    0 -> ViewAdvanced()
                    1 -> ViewAverage()
                    else -> ViewPreliminary()
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AmozeshgamTheme.colors["background"]!!
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(0.1f),
                        text = ""
                    )
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        LinearProgressIndicator(
                            progress = {
                                percent.floatValue / 100
                            },
                            color = AmozeshgamTheme.colors["primary"]!!
                        )
                    }
                    Text(
                        modifier = Modifier.weight(0.6f),
                        text = ""
                    )
                }
            }
        }
    }
}

@Composable
fun ViewPreliminary() {
    val showDetails = remember {
        mutableStateOf(false)
    }
    val showStartExam = remember {
        mutableStateOf(false)
    }
    val showStartStep = remember {
        mutableStateOf(false)
    }
    LazyColumn {
        items(0) {
            UiHandler.DropdownList(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                title = "اموزش"
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "")
                    Text(
                        modifier = Modifier.clickable {

                        },
                        text = "مشاهده جزئیات این بخش",
                        color = AmozeshgamTheme.colors["primary"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"],
                        textDecoration = TextDecoration.Underline
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["primary"]!!),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {}
                    ) {
                        Text(
                            text = "شروع این مرحله ",
                            fontFamily = AmozeshgamTheme.fonts["regular"],
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
    if (showStartStep.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showStartStep.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = "شما فقط دو مرتبه میتوانید در آزمون شرکت کنید. آیا از شروع آزمون مطمئن هستید؟",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.align(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "شروع آزمون",
                        color = AmozeshgamTheme.colors["primary"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                    Text(
                        text = "انصراف",
                        color = AmozeshgamTheme.colors["error"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                }
            }
        }
    }
    if (showStartExam.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showStartStep.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = "شما فقط دو مرتبه میتوانید در آزمون شرکت کنید. آیا از شروع آزمون مطمئن هستید؟",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.align(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "شروع آزمون",
                        color = AmozeshgamTheme.colors["primary"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                    Text(
                        text = "انصراف",
                        color = AmozeshgamTheme.colors["error"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                }
            }
        }
    }
    if (showDetails.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showDetails.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = "جزئیات این بخش",
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    IconButton(
                        onClick = {
                            showDetails.value = false
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = null
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(0) { index ->
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                modifier = Modifier.fillMaxSize(),
                                color = AmozeshgamTheme.colors["textColor"]!!,
                                text = "",
                                textAlign = TextAlign.Right,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ViewAverage() {
    val showDetails = remember {
        mutableStateOf(false)
    }
    val showStartExam = remember {
        mutableStateOf(false)
    }
    val showStartStep = remember {
        mutableStateOf(false)
    }
    LazyColumn {

    }
    if (showStartStep.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showStartStep.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = "شما فقط دو مرتبه میتوانید در آزمون شرکت کنید. آیا از شروع آزمون مطمئن هستید؟",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.align(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "شروع آزمون",
                        color = AmozeshgamTheme.colors["primary"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                    Text(
                        text = "انصراف",
                        color = AmozeshgamTheme.colors["error"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                }
            }
        }
    }
    if (showStartExam.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showStartStep.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = "شما فقط دو مرتبه میتوانید در آزمون شرکت کنید. آیا از شروع آزمون مطمئن هستید؟",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.align(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "شروع آزمون",
                        color = AmozeshgamTheme.colors["primary"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                    Text(
                        text = "انصراف",
                        color = AmozeshgamTheme.colors["error"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                }
            }
        }
    }
    if (showDetails.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showDetails.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = "جزئیات این بخش",
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    IconButton(
                        onClick = {
                            showDetails.value = false
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = null
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(0) { index ->
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                modifier = Modifier.fillMaxSize(),
                                color = AmozeshgamTheme.colors["textColor"]!!,
                                text = "",
                                textAlign = TextAlign.Right,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ViewAdvanced() {
    val showDetails = remember {
        mutableStateOf(false)
    }
    val showStartExam = remember {
        mutableStateOf(false)
    }
    val showStartStep = remember {
        mutableStateOf(false)
    }
    LazyColumn {

    }
    if (showStartStep.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showStartStep.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = "شما فقط دو مرتبه میتوانید در آزمون شرکت کنید. آیا از شروع آزمون مطمئن هستید؟",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.align(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "شروع آزمون",
                        color = AmozeshgamTheme.colors["primary"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                    Text(
                        text = "انصراف",
                        color = AmozeshgamTheme.colors["error"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                }
            }
        }
    }
    if (showStartExam.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showStartStep.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = "شما فقط دو مرتبه میتوانید در آزمون شرکت کنید. آیا از شروع آزمون مطمئن هستید؟",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.align(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "شروع آزمون",
                        color = AmozeshgamTheme.colors["primary"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                    Text(
                        text = "انصراف",
                        color = AmozeshgamTheme.colors["error"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                }
            }
        }
    }
    if (showDetails.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showDetails.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = "جزئیات این بخش",
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    IconButton(
                        onClick = {
                            showDetails.value = false
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = null
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(0) { index ->
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                modifier = Modifier.fillMaxSize(),
                                color = AmozeshgamTheme.colors["textColor"]!!,
                                text = "",
                                textAlign = TextAlign.Right,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                        }
                    }
                }
            }
        }
    }
}
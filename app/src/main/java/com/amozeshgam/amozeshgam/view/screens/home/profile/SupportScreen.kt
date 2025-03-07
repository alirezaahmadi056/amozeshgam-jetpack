package com.amozeshgam.amozeshgam.view.screens.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTickets
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.SupportViewModel

@Composable
fun ViewSupport(navController: NavController, viewModel: SupportViewModel = hiltViewModel()) {
    val supportList = remember {
        mutableStateOf<ApiResponseGetTickets?>(null)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val showAnswerDialog = remember {
        mutableStateOf(false)
    }
    val answerIndex = remember {
        mutableIntStateOf(0)
    }
    UiHandler.ContentWithScaffold(
        title = "پشتیبانی", navigationIcon = {
            IconButton(onClick = { navController.navigate(NavigationScreenHandler.TicketScreen.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = AmozeshgamTheme.colors["textColor"]!!
                )
            }
        }, onBackButtonClick = {
            navController.popBackStack()
        }
    ) {
        UiHandler.ContentWithLoading(
            loading = isLoading.value,
            ifForShowContent = supportList.value != null,
            worker = {
                supportList.value = viewModel.getTickets().await()
                isLoading.value = false
                true
            }, onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            }
        ) {
            AmozeshgamTheme(darkTheme = UiHandler.themeState()) {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .background(AmozeshgamTheme.colors["background"]!!)
                ) {
                    if (supportList.value!!.tickets.isNotEmpty()) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(supportList.value!!.tickets.size) { index ->
                                    UiHandler.DropdownList(
                                        modifier = Modifier.padding(10.dp),
                                        title = supportList.value!!.tickets[index].subject
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(AmozeshgamTheme.colors["background"]!!)
                                                .padding(5.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxSize(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = supportList.value!!.tickets[index].priority,
                                                    color = AmozeshgamTheme.colors["textColor"]!!,
                                                    fontFamily = AmozeshgamTheme.fonts["regular"],
                                                    fontSize = 15.sp
                                                )

                                                Text(
                                                    text = "اولویت",
                                                    color = AmozeshgamTheme.colors["textColor"]!!,
                                                    fontFamily = AmozeshgamTheme.fonts["regular"],
                                                    fontSize = 15.sp
                                                )


                                            }
                                            Row(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxSize(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = supportList.value!!.tickets[index].status,
                                                    color = AmozeshgamTheme.colors["textColor"]!!,
                                                    fontFamily = AmozeshgamTheme.fonts["regular"],
                                                    fontSize = 15.sp
                                                )
                                                Text(
                                                    text = "وضعیت",
                                                    color = AmozeshgamTheme.colors["textColor"]!!,
                                                    fontFamily = AmozeshgamTheme.fonts["regular"],
                                                    fontSize = 15.sp
                                                )
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxSize(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = supportList.value!!.tickets[index].createdAt,
                                                    color = AmozeshgamTheme.colors["textColor"]!!,
                                                    fontFamily = AmozeshgamTheme.fonts["regular"],
                                                    fontSize = 15.sp
                                                )
                                                Text(
                                                    text = "تاریخ ثبت",
                                                    color = AmozeshgamTheme.colors["textColor"]!!,
                                                    fontFamily = AmozeshgamTheme.fonts["regular"],
                                                    fontSize = 15.sp
                                                )
                                            }
                                            if (supportList.value!!.tickets[index].answer != null) {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .fillMaxSize(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {

                                                    Box(
                                                        modifier = Modifier
                                                            .background(
                                                                AmozeshgamTheme.colors["primary"]!!,
                                                                shape = RoundedCornerShape(10.dp)
                                                            )
                                                            .clip(
                                                                RoundedCornerShape(10.dp)
                                                            )
                                                            .clickable {
                                                                answerIndex.intValue = index
                                                                showAnswerDialog.value = true
                                                            }
                                                    ) {
                                                        Text(
                                                            modifier = Modifier.padding(5.dp),
                                                            text = "مشاهده",
                                                            color = Color.White,
                                                            fontFamily = AmozeshgamTheme.fonts["regular"],
                                                            fontSize = 15.sp
                                                        )
                                                    }
                                                    Text(
                                                        text = "عملیات",
                                                        color = AmozeshgamTheme.colors["textColor"]!!,
                                                        fontFamily = AmozeshgamTheme.fonts["regular"],
                                                        fontSize = 15.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (showAnswerDialog.value) {
                                UiHandler.AlertDialog(
                                    onDismiss = {
                                        showAnswerDialog.value = false
                                    },
                                    imageId = AmozeshgamTheme.assets["amozeshgamBanner"]!!,
                                    title = "پاسخ",
                                    description = supportList.value!!.tickets[answerIndex.intValue].answer
                                        ?: ""
                                )
                            }
                            FloatingActionButton(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.BottomEnd),
                                containerColor = AmozeshgamTheme.colors["primary"]!!,
                                shape = RoundedCornerShape(10.dp),
                                onClick = {
                                    navController.navigate(
                                        NavigationScreenHandler.TicketScreen.route
                                    )
                                }) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "درخواست جدید",
                                        fontFamily =AmozeshgamTheme.fonts["regular"],
                                        color = Color.White
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_add),
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.size(250.dp),
                                painter = painterResource(R.drawable.ic_empty_support),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                text = ".شما تا کنون درخواستی برای پشتیبانی ثبت نکرده اید",
                                color = AmozeshgamTheme.colors["textColor"]!!,
                                fontFamily =AmozeshgamTheme.fonts["regular"]
                            )
                            Spacer(modifier = Modifier.height(25.dp))
                            Button(
                                onClick = {
                                    navController.navigate(NavigationScreenHandler.TicketScreen.route)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["primary"]!!)
                            ) {
                                Text(modifier = Modifier.padding(5.dp), text = "درخواست جدید")
                            }
                        }
                    }
                }
            }
        }
    }
}
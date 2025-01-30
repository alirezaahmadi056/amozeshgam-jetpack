package com.amozeshgam.amozeshgam.view.screens.home

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyNotification
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.NotificationItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.NotificationViewModel

@Composable
fun ViewNotification(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel(),
) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val notificationData = remember {
        mutableStateOf<ApiResponseMyNotification?>(null)
    }
    val showNotificationDialog = remember {
        mutableStateOf(false)
    }
    val showNotificationDialogIndex = remember {
        mutableIntStateOf(0)
    }
    UiHandler.ContentWithScaffold(
        title = "اعلانات",
        onBackButtonClick = {
            navController.popBackStack()
        }) {
        UiHandler.ContentWithLoading(
            loading = isLoading.value,
            ifForShowContent = notificationData.value != null,
            worker = {
                notificationData.value = viewModel.getMyNotification().await()
                isLoading.value = false
                true
            },
            onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            }
        ) {
            AmozeshgamTheme(
                darkTheme = UiHandler.themeState()
            ) {
                if (notificationData.value!!.notifications.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(AmozeshgamTheme.colors["background"]!!)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.85f)
                        ) {
                            items(notificationData.value!!.notifications.size) { index ->
                                NotificationItem(
                                    title = notificationData.value!!.notifications[index].title,
                                    date = notificationData.value!!.notifications[index].date,
                                    body = notificationData.value!!.notifications[index].text,
                                ) {
                                    showNotificationDialogIndex.intValue = index
                                    showNotificationDialog.value = true
                                }
                            }
                        }
                    }
                    if (showNotificationDialog.value) {
                        UiHandler.AlertDialog(
                            onDismiss = {
                                showNotificationDialog.value = false
                            },
                            imageId = R.drawable.ic_notification,
                            title = notificationData.value!!.notifications[showNotificationDialogIndex.intValue].title,
                            description = notificationData.value!!.notifications[showNotificationDialogIndex.intValue].text
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AmozeshgamTheme.colors["background"]!!)
                    ) {

                        LottieAnimation(
                            composition = rememberLottieComposition(
                                spec = LottieCompositionSpec.RawRes(
                                    R.raw.animation_empty
                                )
                            ).value
                        )

                    }
                }
            }
        }
    }
}
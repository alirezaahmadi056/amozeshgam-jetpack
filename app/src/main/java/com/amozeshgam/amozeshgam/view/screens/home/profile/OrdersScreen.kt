package com.amozeshgam.amozeshgam.view.screens.home.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllOrders
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.CartItem
import com.amozeshgam.amozeshgam.view.items.OrderReviewItem
import com.amozeshgam.amozeshgam.view.items.OrdersItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.MyOrdersViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ViewMyOrders(navController: NavController, viewModel: MyOrdersViewModel = hiltViewModel()) {
    val ordersData = remember {
        mutableStateOf<ApiResponseAllOrders?>(null)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val showReviewDialog = remember {
        mutableStateOf(false)
    }
    val reviewDialogIndex = remember {
        mutableIntStateOf(0)
    }
    UiHandler.ContentWithScaffold(title = "سفارشات", onBackButtonClick = {
        navController.popBackStack()
    }) {
        UiHandler.ContentWithLoading(
            ifForShowContent = ordersData.value != null,
            loading = isLoading.value,
            worker = {
                ordersData.value = viewModel.getOrdersData().await()
                isLoading.value = false
                true
            },onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            }
        ) {
            AmozeshgamTheme {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .background(AmozeshgamTheme.colors["background"]!!)
                ) {
                    if (ordersData.value!!.orders.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_empty_cart),
                                contentDescription = null
                            )
                            Text(
                                text = "لیست سفارشات شما خالی است.",
                                fontFamily = FontFamily(
                                    Font(R.font.yekan_bakh_regular)
                                )
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(ordersData.value!!.orders.size) { index ->
                                OrdersItem(
                                    orderDate = ordersData.value!!.orders[index].date,
                                    orderType = "",
                                    payment = ordersData.value!!.orders[index].price,
                                    trackNumber = ordersData.value!!.orders[index].trackingCode,
                                    status = ordersData.value!!.orders[index].status,
                                    orderNumber = "سفارش شماره ی${index+1}",
                                    onReviewClick = {
                                        showReviewDialog.value = true
                                        reviewDialogIndex.intValue = index
                                    }
                                )
                            }
                        }
                    }
                }
                if (showReviewDialog.value) {
                    UiHandler.CustomDialog(
                        onDismiss = {
                            showReviewDialog.value = false
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "سفارشات",
                                color = AmozeshgamTheme.colors["textColor"]!!,
                                fontFamily = FontFamily(Font(R.font.yekan_bakh_bold))
                            )
                            LazyColumn {
                                items(ordersData.value!!.orders[reviewDialogIndex.intValue].roadMaps.size) { index ->
                                    OrderReviewItem(
                                        name = ordersData.value!!.orders[reviewDialogIndex.intValue].roadMaps[index].title,
                                        image = ordersData.value!!.orders[reviewDialogIndex.intValue].roadMaps[index].image
                                    )
                                }
                                items(ordersData.value!!.orders[reviewDialogIndex.intValue].courses.size) { index ->
                                    OrderReviewItem(
                                        name = ordersData.value!!.orders[reviewDialogIndex.intValue].courses[index].title,
                                        image = ordersData.value!!.orders[reviewDialogIndex.intValue].courses[index].image
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
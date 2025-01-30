package com.amozeshgam.amozeshgam.view.screens.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMyTransactions
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.OrdersItem
import com.amozeshgam.amozeshgam.view.items.TransactionItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.MyTransactionViewModel

@Composable
fun ViewMyTransaction(
    navController: NavController,
    viewModel: MyTransactionViewModel = hiltViewModel(),
) {
    val transactionData = remember {
        mutableStateOf<ApiResponseGetMyTransactions?>(null)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    UiHandler.ContentWithScaffold(
        title = "تراکنش ها",
        onBackButtonClick = { navController.popBackStack() }) {
        UiHandler.ContentWithLoading(
            loading = isLoading.value,
            worker = {
                transactionData.value = viewModel.getTransactionsData().await()
                isLoading.value = false
                true
            },
            onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            },
            ifForShowContent = transactionData.value != null
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(AmozeshgamTheme.colors["background"]!!)
            ) {
                if (transactionData.value!!.transactions.isEmpty()) {
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
                        Text(text = "تراکنشی وجود ندارد.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(transactionData.value!!.transactions.size) { index: Int ->
                            TransactionItem(
                                transactionDate = transactionData.value!!.transactions[index].date,
                                transactionType ="",
                                payment = transactionData.value!!.transactions[index].price.toString(),
                                trackNumber = transactionData.value!!.transactions[index].trackingSerial,
                                transactionNumber = "سفارش شماره ی${index+1}",
                                status = transactionData.value!!.transactions[index].status == "accept"
                            )
                        }
                    }
                }
            }
        }
    }
}
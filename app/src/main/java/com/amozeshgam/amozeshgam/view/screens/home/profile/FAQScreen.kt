package com.amozeshgam.amozeshgam.view.screens.home.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseFaq
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.FaqViewModel

@Composable
fun ViewFAQ(navController: NavController, viewModel: FaqViewModel = hiltViewModel()) {
    val isLoading = remember {
        mutableStateOf(false)
    }
    val faqData = remember {
        mutableStateOf<ApiResponseFaq?>(null)
    }
    UiHandler.ContentWithScaffold(
        title = "سوالات متداول",
        onBackButtonClick = {
            navController.popBackStack()
        }
    ) {
        UiHandler.ContentWithLoading(
            loading = isLoading.value,
            ifForShowContent = faqData.value != null,
            worker = {
                isLoading.value = false
                faqData.value = viewModel.getFaqData().await()
                true
            },
            onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            }
        ) {
            AmozeshgamTheme {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(faqData.value!!.faq.size) { index ->
                        UiHandler.DropdownList(
                            title = faqData.value!!.faq[index].question
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Right,
                                fontFamily = AmozeshgamTheme.fonts["regular"],
                                text = faqData.value!!.faq[index].answer
                            )
                        }
                    }
                }
            }
        }
    }
}
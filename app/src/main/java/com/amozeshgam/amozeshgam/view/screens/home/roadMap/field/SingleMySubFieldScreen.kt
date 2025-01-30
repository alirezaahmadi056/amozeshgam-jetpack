package com.amozeshgam.amozeshgam.view.screens.home.roadMap.field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleField
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
    val singleSubField = remember {
        mutableStateOf<ApiResponseSingleField?>(null)
    }
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = singleSubField.value != null,
        worker = {
            singleSubField.value = viewModel.getFieldData(id = 1).await()
            isLoading.value = false
            true
        },onDismissRequestForDefaultErrorHandler = {
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
            //check suggest course
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
        }
    }
}

@Composable
fun ViewPreliminary() {
    LazyColumn {

    }
}

@Composable
fun ViewAverage() {
    LazyColumn {

    }
}

@Composable
fun ViewAdvanced() {
    LazyColumn {

    }
}
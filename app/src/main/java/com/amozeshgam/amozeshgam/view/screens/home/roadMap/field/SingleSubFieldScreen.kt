package com.amozeshgam.amozeshgam.view.screens.home.roadMap.field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleSubField
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.roadMap.SubFieldViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ViewSingleSubField(
    navController: NavController,
    viewModel: SubFieldViewModel = hiltViewModel(),
) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val subFieldData = remember {
        mutableStateOf<ApiResponseSingleSubField?>(null)
    }
    val subFieldId = remember {
        navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    }

    UiHandler.ContentWithLoading(loading = isLoading.value,
        ifForShowContent = subFieldData.value != null,
        worker = {
            subFieldData.value = viewModel.getSubFieldData(id = subFieldId).await()
            isLoading.value = false
            true
        },
        onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }) {
        AmozeshgamTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "مسیریادگیری",
                        fontSize = 25.sp,
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .aspectRatio(16f / 9f)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = null,
                            contentDescription = null
                        )
                    }
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = ":پس از گذراندن این مسیر یادگیری تو میتونی",
                        fontSize = 25.sp,
                        fontFamily = AmozeshgamTheme.fonts["regular"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp)
                    ) {

                    }
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "با مسیر پیش رو میتونی نرم افزارهای زیر رو بسازی",
                        fontSize = 25.sp,
                        fontFamily = AmozeshgamTheme.fonts["regular"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    FlowRow(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ) {

                    }
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "ویژگی های طرح",
                        fontSize = 25.sp,
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp)
                    ) {

                    }
                    AsyncImage(
                        modifier = Modifier.aspectRatio(2.79f / 1f),
                        model = null,
                        contentDescription = null
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {

                        }
                        Button(
                            onClick = {}
                        ) { }
                    }
                }
            }
        }
    }
}
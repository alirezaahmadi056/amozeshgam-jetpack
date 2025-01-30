package com.amozeshgam.amozeshgam.view.screens.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyRoadMap
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.MyRoadMapItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.MyRoadMapViewModel

@Composable
fun ViewMyRoadMap(
    viewModel: MyRoadMapViewModel = hiltViewModel(),
    navController: NavController,
) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val myRoadMapData = remember {
        mutableStateOf<ApiResponseMyRoadMap?>(null)
    }

    UiHandler.ContentWithScaffold(
        title = "مسیرهای یادگیری من",
        onBackButtonClick = {
            navController.popBackStack()
        }
    ) {
        UiHandler.ContentWithLoading(
            loading = isLoading.value,
            ifForShowContent = myRoadMapData.value != null,
            worker = {
                myRoadMapData.value = viewModel.getMyRoadMap().await()
                isLoading.value = false
                true
            },
            onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            }
        ) {
            AmozeshgamTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AmozeshgamTheme.colors["background"]!!)
                ) {
                    if (myRoadMapData.value!!.roadMap.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            LottieAnimation(
                                composition = rememberLottieComposition(
                                    spec = LottieCompositionSpec.RawRes(
                                        R.raw.animation_empty
                                    )
                                ).value
                            )

                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                        ) {
                            items(myRoadMapData.value!!.roadMap.size) { index ->
                                MyRoadMapItem(
                                    roadMapImage = myRoadMapData.value!!.roadMap[index].image,
                                    roadMapName = myRoadMapData.value!!.roadMap[index].title,
                                    percentRoadmap = myRoadMapData.value!!.roadMap[index].percent.toFloat()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

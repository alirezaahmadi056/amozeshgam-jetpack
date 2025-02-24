package com.amozeshgam.amozeshgam.view.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTour
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.splash.SplashViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewTour(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {
    val bottomSheetIsLocked = remember {
        mutableStateOf(true)
    }
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            it != SheetValue.Hidden
        }
    )
    val screenSize = LocalConfiguration.current
    val tourData = remember {
        mutableStateOf<ApiResponseGetTour?>(null)
    }
    val currentIndex = remember {
        mutableIntStateOf(0)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val coroutine = rememberCoroutineScope()
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = (tourData.value?.tours?.size ?: 0) > 0,
        worker = {
            tourData.value = viewModel.apiGetTourData().await()
            isLoading.value = false
            true
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AmozeshgamTheme.colors["background"]!!)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                model = tourData.value!!.tours[currentIndex.intValue].image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(id = R.drawable.ic_app_banner_day)
            )
            if (bottomSheetIsLocked.value) {
                UiHandler.TourModalBottomSheet(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height((screenSize.screenHeightDp / 2.2).dp),
                    state = modalBottomSheetState,
                    title = tourData.value!!.tours[currentIndex.intValue].title,
                    body = tourData.value!!.tours[currentIndex.intValue].body,
                    totalStep = tourData.value!!.tours.size,
                    currentState = currentIndex.intValue,
                    textButton = if (tourData.value!!.tours.size - 1 == currentIndex.intValue) "ورود" else "بعدی",
                    onBackClick = {
                        currentIndex.intValue -= 1
                    },
                ) {
                    if (tourData.value!!.tours.size - 1 == currentIndex.intValue) {
                        viewModel.saveTourData()
                        coroutine.launch {
                            modalBottomSheetState.hide()
                        }
                        bottomSheetIsLocked.value = false
                        navController.navigate(NavigationScreenHandler.LoginScreenOne.route)
                    } else {
                        currentIndex.intValue += 1
                    }
                }
            }
        }

    }

}
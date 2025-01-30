package com.amozeshgam.amozeshgam.view.screens.home.podcasts

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllPodcasts
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.PodcastItem
import com.amozeshgam.amozeshgam.viewmodel.home.podcasts.PodcastViewModel

@Composable
fun ViewPodcasts(navController: NavController, viewModel: PodcastViewModel = hiltViewModel()) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val podcastsData = remember {
        mutableStateOf<ApiResponseAllPodcasts?>(null)
    }
    UiHandler.ContentWithLoading(loading = isLoading.value, worker = {
        podcastsData.value = viewModel.getPodcastsData().await()
        isLoading.value = false
        true
    }, ifForShowContent = podcastsData.value != null,
        onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }) {
        CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
            LazyVerticalGrid(modifier = Modifier.padding(5.dp), columns = GridCells.Fixed(2)) {
                items(podcastsData.value!!.podcasts.size) { index ->
                    PodcastItem(
                        imageUrl = podcastsData.value!!.podcasts[index].image,
                        text = podcastsData.value!!.podcasts[index].title,
                        speech = podcastsData.value!!.podcasts[index].speaker,
                        onClick = {
                            navController.navigate("${NavigationHandler.PodcastPlayerScreen.route}/${podcastsData.value!!.podcasts[index].id}")
                        }
                    )
                }
            }
        }
    }
}
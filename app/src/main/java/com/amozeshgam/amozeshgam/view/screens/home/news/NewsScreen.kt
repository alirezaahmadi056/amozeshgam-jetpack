package com.amozeshgam.amozeshgam.view.screens.home.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseExplorer
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.news.NewsViewModel

@Composable
fun ViewNews(navController: NavController, viewModel: NewsViewModel = hiltViewModel()) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val newsData = remember {
        mutableStateOf<ApiResponseExplorer?>(null)
    }
    val context = LocalContext.current
    UiHandler.ContentWithLoading(
        ifForShowContent = newsData.value != null,
        loading = isLoading.value,
        worker = {
            newsData.value = viewModel.getNewsData().await()
            isLoading.value = false
            true
        },
        onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }
    ) {
        AmozeshgamTheme {
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive((LocalConfiguration.current.screenWidthDp / 3).dp)) {
                items(newsData.value!!.data.size) { index ->
                    if (index % 6 == 0 && newsData.value!!.data[index].important) {
                        AsyncImage(modifier = Modifier
                            .clickable {
                                navController.navigate("${NavigationHandler.SingleNewsScreen.route}/${newsData.value!!.data[index].id}")
                            }
                            .height(300.dp),
                            model = newsData.value!!.data[index].media,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                    } else {
                        AsyncImage(modifier = Modifier
                            .clickable {
                                navController.navigate("${NavigationHandler.SingleNewsScreen.route}/${newsData.value!!.data[index].id}")

                            }
                            .height(150.dp),
                            model = newsData.value!!.data[index].media,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
        }
    }
}
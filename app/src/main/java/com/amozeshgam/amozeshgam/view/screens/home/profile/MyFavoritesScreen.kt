package com.amozeshgam.amozeshgam.view.screens.home.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMyFavorites
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.FavoritesItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.MyFavoritesViewModel

@Composable
fun ViewMyFavorites(
    navController: NavController,
    viewModel: MyFavoritesViewModel = hiltViewModel(),
) {
    val favoritesData = remember {
        mutableStateOf<ApiResponseGetMyFavorites?>(null)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current

    UiHandler.ContentWithScaffold(title = "علاقه مندی ها", onBackButtonClick = {
        navController.popBackStack()
    }) {
        UiHandler.ContentWithLoading(
            loading = isLoading.value,
            ifForShowContent = favoritesData.value != null,
            worker = {
                favoritesData.value = viewModel.getMyFavorites().await()
                isLoading.value = false
                true
            }, onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .background(AmozeshgamTheme.colors["background"]!!)
                    .fillMaxSize()
            ) {
                if (favoritesData.value!!.favorites.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.my_favorite),
                            contentDescription = null
                        )
                        Text(
                            text = "دوره ای در لیست علاقه مندی های شما نیست.",
                            fontFamily = FontFamily(
                                Font(R.font.yekan_bakh_regular)
                            )
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(favoritesData.value!!.favorites.size) { index ->
                            FavoritesItem(
                                image = favoritesData.value!!.favorites[index].image,
                                title = favoritesData.value!!.favorites[index].title,
                                onViewClick = {
                                    Toast.makeText(
                                        context,
                                        "دیپ لینک فعال نیست",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onRemoveClick = {
                                    isLoading.value = true
                                    viewModel.removeFromMyFavorite(
                                        favoritesData.value!!.favorites[index].id,
                                        favoritesData.value!!.favorites[index].type
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            viewModel.deletedFavorite.observe(lifecycle) {
                if (it) {
                    Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "خطا در حذف علاقه مندی", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
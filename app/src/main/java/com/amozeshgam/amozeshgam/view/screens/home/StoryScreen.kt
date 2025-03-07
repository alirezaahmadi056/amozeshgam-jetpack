package com.amozeshgam.amozeshgam.view.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetStory
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.handler.openLink
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.StoryViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewStory(navController: NavController, viewModel: StoryViewModel = hiltViewModel()) {
    val sliderValue = remember {
        mutableFloatStateOf(0f)
    }
    val sliderTime = remember {
        mutableFloatStateOf(viewModel.getStoryTime().toFloat())
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val storyId = remember {
        navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    }
    val storyData = remember {
        mutableStateOf<ApiResponseGetStory?>(null)
    }

    val sliderState = remember {
        SliderState(
            sliderValue.floatValue, valueRange = 0f..60f
        )
    }
    val context = LocalContext.current
    AmozeshgamTheme(
        darkTheme = UiHandler.themeState()
    ) {
        UiHandler.ContentWithLoading(
            loading = isLoading.value,
            ifForShowContent = storyData.value != null,
            worker = {
                storyData.value = viewModel.getStoryData(storyId).await()
                isLoading.value = false
                true
            },
            onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                if (!storyData.value!!.media.endsWith(".gif")) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = storyData.value!!.media,
                        contentDescription = null
                    )
                } else {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(
                            model = storyData.value!!.media,
                            imageLoader = ImageLoader.Builder(context).components {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                    add(ImageDecoderDecoder.Factory())
                                } else {
                                    add(GifDecoder.Factory())
                                }
                            }.build()
                        ),
                        contentDescription = null
                    )
                }
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Slider(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(15.dp),
                        state = sliderState,
                        track = {
                            SliderDefaults.Track(
                                sliderState = sliderState,
                                modifier = Modifier.height(5.dp),
                                thumbTrackGapSize = 0.dp,
                                colors = SliderDefaults.colors(
                                    activeTrackColor = AmozeshgamTheme.colors["sliderActiveColor"]!!,
                                    inactiveTrackColor = AmozeshgamTheme.colors["sliderInActiveColor"]!!,
                                    thumbColor = AmozeshgamTheme.colors["sliderInActiveColor"]!!,
                                    activeTickColor = AmozeshgamTheme.colors["sliderActiveColor"]!!,
                                    inactiveTickColor = AmozeshgamTheme.colors["sliderInActiveColor"]!!,
                                ),
                            )
                        },
                        thumb = {

                        }
                    )
                }

                Button(
                    onClick = {
                        context.openLink(storyData.value!!.link)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmozeshgamTheme.colors["primary"]!!,
                        disabledContainerColor = AmozeshgamTheme.colors["disableContainer"]!!,
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = "مشاهده", style = TextStyle(color = Color.White))
                }
            }
            LaunchedEffect(Unit) {
                while (sliderTime.floatValue >= 0f) {
                    delay(1000)
                    sliderTime.floatValue -= 1f
                    sliderState.value += 1f
                }
            }
            LaunchedEffect(sliderTime.floatValue) {
                if (sliderTime.floatValue <= 0f) {
                    navController.navigate(NavigationScreenHandler.HomeScreen.route)
                }
            }
            LaunchedEffect(Unit) {
                sliderState.onValueChangeFinished = {
                    sliderTime.floatValue = 60 - sliderState.value
                }
            }

        }
    }
}
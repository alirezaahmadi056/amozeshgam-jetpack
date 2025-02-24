package com.amozeshgam.amozeshgam.view.screens.home.podcasts

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetPodcast
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.podcasts.PodcastPlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPodcastPlayer(
    navController: NavController, viewModel: PodcastPlayerViewModel = hiltViewModel(),
) {
    val isPlaying = viewModel.isPlaying.collectAsState()
    val enabledRepeatMode = viewModel.enabledRepeatMode.collectAsState()
    val navPodcastId = remember {
        navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    }
    val podcastId = remember {
        mutableIntStateOf(navPodcastId)
    }
    val isLoading = remember {
        mutableStateOf(false)
    }
    val speech = remember {
        mutableStateOf("آموزشگام")
    }
    val title = remember {
        mutableStateOf("آموزشگام")
    }
    val podcastData = remember {
        mutableStateOf<ApiResponseGetPodcast?>(null)
    }
    val currentDuration = remember {
        mutableStateOf("")
    }
    val maxDuration = remember {
        mutableStateOf("")
    }
    val showErrorDialog = remember {
        mutableStateOf(false)
    }
    val maxDurationLong = remember {
        mutableLongStateOf(0L)
    }
    val maxDurationSeconds = remember {
        mutableIntStateOf((maxDurationLong.longValue / 1000).toInt())
    }
    val currentDurationLong = remember {
        mutableLongStateOf(0L)
    }
    val currentDurationSeconds = remember {
        mutableIntStateOf((currentDurationLong.longValue / 1000).toInt())
    }
    val errorLoading = remember {
        mutableStateOf(false)
    }
    val liked = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val readyForPlay = viewModel.readyForPlay.collectAsState()
    val onBackPress = LocalOnBackPressedDispatcherOwner.current
    val sliderValue = remember {
        mutableFloatStateOf(0f)
    }
    val sliderController = remember {
        SliderState(
            value = sliderValue.floatValue,
            valueRange = 0f..100f,
        )
    }
    UiHandler.ContentWithShimmerLoading(
        loading = isLoading.value,
        modifier = Modifier.fillMaxSize(),
        worker = {
            podcastData.value = viewModel.getPodcastData(id = podcastId.intValue).await()
            viewModel.startPodcastService()
            viewModel.loadData(
                podcastData.value?.voice.toString(),
                podcastData.value?.title.toString()
            )
            liked.value = podcastData.value?.isLike ?: false
            isLoading.value = false
            showErrorDialog.value = podcastData.value == null
        },
    ) { modifier ->
        if (!showErrorDialog.value) {
            Column(
                modifier = modifier, verticalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(5f / 5f),
                    model = podcastData.value?.image.toString(),
                    contentDescription = null
                )
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        text = title.value,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_bold)),
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    UiHandler.AnythingRow(modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 10.dp),
                        itemOne = {
                            Text(
                                modifier = Modifier.align(Alignment.End),
                                text = speech.value,
                                fontFamily = FontFamily(
                                    Font(R.font.yekan_bakh_regular)
                                ),
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                        },
                        itemTwo = {
                            Icon(
                                painter = painterResource(R.drawable.ic_person),
                                contentDescription = null,
                                tint = AmozeshgamTheme.colors["textColor"]!!
                            )
                        })
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Slider(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(15.dp),
                        state = sliderController,
                        track = {
                            SliderDefaults.Track(
                                sliderState = sliderController,
                                modifier = Modifier.height(5.dp),
                                colors = SliderDefaults.colors(
                                    activeTrackColor = AmozeshgamTheme.colors["primary"]!!,
                                    activeTickColor = AmozeshgamTheme.colors["primary"]!!,
                                ),
                            )
                        },
                        thumb = {
                            Box(
                                modifier = Modifier
                                    .size(15.dp)
                                    .clip(RoundedCornerShape(100))
                                    .background(AmozeshgamTheme.colors["primary"]!!)
                            )
                        })
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = currentDuration.value,
                            color = AmozeshgamTheme.colors["textColor"]!!
                        )
                        Text(
                            text = maxDuration.value,
                            color = AmozeshgamTheme.colors["textColor"]!!
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        viewModel.repeatPodcast()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_repeat),
                            contentDescription = null,
                            tint = if (enabledRepeatMode.value) AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["textColor"]!!
                        )
                    }
                    IconButton(onClick = {
                        isLoading.value = true
                        viewModel.pausePodcast()
                        viewModel.release()
                        podcastId.intValue -= 1
                        maxDuration.value = ""
                        sliderController.value = 0f
                        currentDuration.value = "0:0"
                        viewModel.pausePodcast()
                        viewModel.resetPosition()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_backward),
                            contentDescription = null,
                            tint = AmozeshgamTheme.colors["textColor"]!!
                        )
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .size(65.dp)
                            .shadow(
                                10.dp,
                                ambientColor = AmozeshgamTheme.colors["primary"]!!,
                                spotColor = AmozeshgamTheme.colors["primary"]!!,
                                shape = RoundedCornerShape(100)
                            ),
                        onClick = {
                            if (readyForPlay.value) {
                                if (isPlaying.value) {
                                    viewModel.pausePodcast()
                                } else {
                                    maxDurationLong.longValue = viewModel.playPodcast()
                                    maxDurationSeconds.intValue =
                                        (maxDurationLong.longValue / 1000).toInt()
                                    maxDuration.value =
                                        "${maxDurationSeconds.intValue / 60}:${maxDurationSeconds.intValue % 60}"
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "درحال اماده سازی صبر کنید",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        shape = RoundedCornerShape(100),
                        elevation = FloatingActionButtonDefaults.elevation(10.dp),
                        containerColor = AmozeshgamTheme.colors["primary"]!!,
                    ) {
                        if (readyForPlay.value) {
                            Image(
                                modifier = Modifier.padding(17.dp),
                                painter = if (!isPlaying.value) painterResource(id = R.drawable.ic_play) else painterResource(
                                    id = R.drawable.ic_stop
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth
                            )
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White
                            )
                        }
                    }
                    IconButton(onClick = {
                        isLoading.value = true
                        viewModel.pausePodcast()
                        viewModel.release()
                        podcastId.intValue += 1
                        maxDuration.value = ""
                        sliderController.value = 0f
                        currentDuration.value = "0:0"
                        viewModel.pausePodcast()
                        viewModel.resetPosition()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_forward),
                            contentDescription = null,
                            tint = AmozeshgamTheme.colors["textColor"]!!
                        )
                    }
                    IconButton(onClick = {
                        if (!liked.value) {
                            liked.value = true
                            viewModel.likePodcast(podcastId = podcastId.intValue)
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(if (liked.value) R.drawable.ic_like_fill else R.drawable.ic_like),
                            contentDescription = null,
                            tint = if (liked.value) AmozeshgamTheme.colors["errorColor"]!! else AmozeshgamTheme.colors["textColor"]!!
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                LaunchedEffect(Unit) {
                    sliderController.onValueChangeFinished = {
                        viewModel.seekTo(((sliderController.value / 100) * maxDurationLong.longValue.toInt()).toLong())
                    }
                }
            }
            LaunchedEffect(isPlaying.value) {
                while (isPlaying.value) {
                    currentDurationLong.longValue = viewModel.getCurrentPosition()
                    currentDurationSeconds.intValue =
                        (currentDurationLong.longValue / 1000).toInt()
                    currentDuration.value =
                        "${currentDurationSeconds.intValue / 60}:${currentDurationSeconds.intValue % 60}"
                    sliderController.value =
                        ((currentDurationLong.longValue.toInt() * 100) / maxDurationLong.longValue.toInt()).toFloat()
                    if (currentDurationSeconds.intValue == maxDurationSeconds.intValue) {
                        sliderController.value = 0f
                        currentDuration.value = "0:0"
                        if (!enabledRepeatMode.value) {
                            viewModel.pausePodcast()
                            viewModel.resetPosition()
                            break
                        }
                    }
                    delay(1)
                }
            }
            LaunchedEffect(Unit) {
                onBackPress?.onBackPressedDispatcher?.addCallback(object :
                    OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        viewModel.pausePodcast()
                        viewModel.resetPosition()
                        navController.popBackStack()
                    }
                })
            }
        } else {
            Box(modifier = modifier) {
                UiHandler.ErrorDialog(imageId = R.drawable.ic_mic, text = "پادکستی پیدا نشد") {
                    if (errorLoading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = AmozeshgamTheme.colors["primary"]!!
                        )
                    } else {
                        Text(
                            modifier = Modifier.clickable {
                                errorLoading.value = true
                                CoroutineScope(Dispatchers.IO).launch {
                                    podcastData.value =
                                        viewModel.getPodcastData(id = podcastId.intValue).await()
                                    CoroutineScope(Dispatchers.Main).launch {
                                        viewModel.loadData(
                                            podcastData.value?.voice.toString(),
                                            podcastData.value?.title.toString()
                                        )
                                        errorLoading.value = false
                                        showErrorDialog.value = podcastData.value == null
                                    }
                                }
                            },
                            text = "تلاش مجدد",
                            color = AmozeshgamTheme.colors["primary"]!!
                        )
                    }
                    Text(
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }, text = "خروج",
                        color = AmozeshgamTheme.colors["errorColor"]!!
                    )
                }
            }
        }
        BackHandler {
            viewModel.clear()
        }
    }
}
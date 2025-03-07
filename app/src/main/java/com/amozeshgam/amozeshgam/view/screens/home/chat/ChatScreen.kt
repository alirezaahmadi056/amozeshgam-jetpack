package com.amozeshgam.amozeshgam.view.screens.home.chat

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMessagesData
import com.amozeshgam.amozeshgam.handler.RemoteStateHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.AdminChatItem
import com.amozeshgam.amozeshgam.view.items.UserChatItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.ChatViewModel
import kotlinx.coroutines.launch

@Composable
fun ViewChat(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val source =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resId = R.raw.animation_chat))
    val message = remember {
        mutableStateOf("")
    }
    val lazyColumnState = rememberLazyListState()

    val isLoading = remember {
        mutableStateOf(true)
    }
    val messageData = remember {
        mutableStateListOf<ApiResponseGetMessagesData>()
    }
    val coroutine = rememberCoroutineScope()
    val messageSenderState = viewModel.messageSenderState.collectAsState()
    val context = LocalContext.current
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        worker = {
            viewModel.getAllMessages().await().also {
                messageData.addAll((it?.message ?: listOf()).toMutableStateList())
            }

            viewModel.onStart()
            isLoading.value = false
            true
        },
        onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }
    ) {
        AmozeshgamTheme(
            darkTheme = UiHandler.themeState()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AmozeshgamTheme.colors["background"]!!)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (messageData.isEmpty()) {
                    Box {
                        LottieAnimation(
                            modifier = Modifier.align(Alignment.Center),
                            composition = source.value,
                            iterations = LottieConstants.IterateForever
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "گفت و گو با مشاورین آموزشگام",
                                fontSize = 25.sp,
                                fontFamily = AmozeshgamTheme.fonts["black"],
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                            Text(
                                modifier = Modifier.wrapContentSize(),
                                text = "..پاسخگوی سوالات شما",
                                fontFamily = AmozeshgamTheme.fonts["bold"],
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                        }

                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = lazyColumnState
                    ) {
                        items(messageData.size) { index ->
                            if (messageData[index].senderType == "user") {
                                UserChatItem(
                                    messageData[index].message,
                                    messageData[index].time
                                )
                            } else {
                                AdminChatItem(
                                    messageData[index].message,
                                    messageData[index].time
                                )
                            }
                        }
                    }
                    SideEffect {
                        coroutine.launch {
                            lazyColumnState.scrollToItem(messageData.size)
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .heightIn(min = 60.dp, max = 250.dp)
                        .shadow(
                            25.dp,
                            ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                            spotColor = AmozeshgamTheme.colors["shadowColor"]!!
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = AmozeshgamTheme.colors["background"]!!,
                    ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UiHandler.CustomTextField(
                            modifier = Modifier
                                .weight(0.82f)
                                .heightIn(min = 50.dp),
                            value = message.value,
                            onValueChange = {
                                message.value = it
                            },

                            trailingIcon =
                            if (GlobalUiModel.textProviderData.value != null || viewModel.suggestionHandler.getTextFromClipBoard() != null) {
                                {
                                    Image(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .size(20.dp)
                                            .clickable {
                                                message.value =
                                                    GlobalUiModel.textProviderData.value
                                                        ?: viewModel.suggestionHandler.getTextFromClipBoard()
                                                                ?: ""
                                                GlobalUiModel.textProviderData.value = null
                                            },
                                        painter = painterResource(id = R.drawable.ic_lamp),
                                        contentDescription = null,
                                    )
                                }

                            } else null,
                            placeholder = "پیام خود را بنویسید"

                        )
                        FloatingActionButton(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(0.18f)
                                .size(50.dp)
                                .clip(CircleShape),

                            shape = CircleShape,
                            onClick = {
                                if (message.value.isNotEmpty()) {
                                    viewModel.sendSupportMessage(message = message.value)
                                } else {
                                    Toast.makeText(context, "پیامی وجود ندارد", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            },
                            containerColor = AmozeshgamTheme.colors["primary"]!!
                        ) {
                            if (messageSenderState.value != RemoteStateHandler.LOADING) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_arrow_up),
                                    contentDescription = null
                                )
                            } else {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            LaunchedEffect(messageSenderState.value) {
                when (messageSenderState.value) {
                    RemoteStateHandler.OK -> {
                        val newMessage = ApiResponseGetMessagesData(
                            message = message.value,
                            senderType = "user",
                            time = viewModel.getTime()
                        )
                        messageData.add(newMessage)
                        message.value = ""
                        coroutine.launch {
                            lazyColumnState.animateScrollToItem(messageData.size)
                        }
                        Toast.makeText(context, "پیام شما ارسال شد", Toast.LENGTH_SHORT).show()
                    }

                    RemoteStateHandler.BAD_RESPONSE -> {
                        Toast.makeText(context, "خطا در ارسال پیام", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }


            }
        }
    }
}
package com.amozeshgam.amozeshgam.view.screens.home.chat

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMessages
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.AdminChatItem
import com.amozeshgam.amozeshgam.view.items.UserChatItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.ChatViewModel

@Composable
fun ViewAiChat(navController: NavController, viewModel: ChatViewModel = hiltViewModel()) {
    val source =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resId = R.raw.animation_ai_chat))
    val message = remember {
        mutableStateOf("")
    }
    val lazyColumnTopPadding = remember {
        mutableStateOf(0.dp)
    }

    val isLoading = remember {
        mutableStateOf(false)
    }
    val messageData = remember {
        mutableStateOf<ApiResponseGetMessages?>(null)
    }
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = true,
        worker = {
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
                if (true) {
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(2.dp),
                                text = "گفت و گو با هوش مصنوعی آموزشگام",
                                fontSize = 22.sp,
                                fontFamily = FontFamily(Font(R.font.yekan_bakh_black)),
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                            Text(
                                modifier = Modifier.wrapContentSize(),
                                text = "..پاسخگوی سوالات شما",
                                fontFamily = FontFamily(Font(R.font.yekan_bakh_semibold)),
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                        }

                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .animateContentSize()
                            .weight(1f)
                            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(messageData.value!!.message.size) { index ->
                            if (messageData.value!!.message[index].senderType == "user") {
                                UserChatItem(
                                    messageData.value!!.message[index].message,
                                    messageData.value!!.message[index].time
                                )
                            } else {
                                AdminChatItem(
                                    messageData.value!!.message[index].message,
                                    messageData.value!!.message[index].time
                                )
                            }
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
                        )
                        .onGloballyPositioned {
                            lazyColumnTopPadding.value = (it.size.height + 40).dp
                        },
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

                            },
                            containerColor = AmozeshgamTheme.colors["primary"]!!
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_arrow_up),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}
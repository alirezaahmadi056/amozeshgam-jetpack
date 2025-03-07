package com.amozeshgam.amozeshgam.view.screens.home.roadMap.question

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAiQuestion
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.OptionItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.view.ui.theme.primaryColorDark
import com.amozeshgam.amozeshgam.viewmodel.home.roadMap.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAiQuestion(viewModel: QuestionViewModel = hiltViewModel(), navController: NavController) {
    val currentIndex = remember {
        mutableIntStateOf(0)
    }

    val sliderValue = remember {
        mutableFloatStateOf(0f)
    }
    val sliderState = remember {
        SliderState(value = sliderValue.floatValue, valueRange = 0f..20f)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val questionData = remember {
        mutableStateOf<ApiResponseAiQuestion?>(null)
    }
    val currentOptionSelected = remember {
        mutableIntStateOf(-1)
    }

    val screenSize = LocalConfiguration.current
    val numberOfQuestion = buildAnnotatedString {
        withStyle(style = SpanStyle(color = AmozeshgamTheme.colors["primary"]!!)) {
            append((currentIndex.intValue + 1).toString())
        }
        withStyle(style = SpanStyle(color = AmozeshgamTheme.colors["textColor"]!!)) {
            append("/" + questionData.value?.questions?.size.toString())
        }
    }
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = questionData.value != null,
        worker = {
            questionData.value = viewModel.getAiQuestions()
            isLoading.value = false
            true
        },
        onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "سوال", color = AmozeshgamTheme.colors["textColor"]!!)
                Text(
                    text = numberOfQuestion,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                )
                Spacer(modifier = Modifier.height(10.dp))
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Slider(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .height(15.dp),
                        state = sliderState,
                        track = {
                            SliderDefaults.Track(
                                sliderState = sliderState,
                                modifier = Modifier.height(5.dp),
                                thumbTrackGapSize = 0.dp,
                                colors = SliderDefaults.colors(
                                    activeTrackColor = AmozeshgamTheme.colors["primary"]!!,
                                    inactiveTrackColor = AmozeshgamTheme.colors["sliderInActiveColor"]!!,
                                    thumbColor = AmozeshgamTheme.colors["sliderInActiveColor"]!!,
                                    activeTickColor = AmozeshgamTheme.colors["primary"]!!,
                                    inactiveTickColor = AmozeshgamTheme.colors["sliderInActiveColor"]!!,
                                ),
                            )
                        },
                        thumb = {

                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .aspectRatio(2.79f / 1f)
                        .clip(RoundedCornerShape(15.dp))
                        .background(primaryColorDark)
                        .paint(
                            painter = painterResource(R.drawable.bg_question),
                            contentScale = ContentScale.FillBounds
                        )


                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = questionData.value!!.questions[currentIndex.intValue].question,
                        textAlign = TextAlign.End,
                        fontSize = 25.sp,
                        fontFamily = AmozeshgamTheme.fonts["regular"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                }
                if (questionData.value!!.questions[currentIndex.intValue].options != null){
                    Log.i("jjj", "ViewAiQuestion: hello")
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = (screenSize.screenHeightDp / 2).dp)
                    ) {
                        items(
                            questionData.value!!.questions[currentIndex.intValue].options!!.size
                        ) { index ->
                            OptionItem(
                                text = questionData.value!!.questions[currentIndex.intValue].options?.getOrNull(index) ?: "",
                                tag = index,
                                currentTag = currentOptionSelected.intValue
                            ) {
                                currentOptionSelected.intValue = index
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (currentIndex.intValue != 0) {
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        onClick = {
                            currentIndex.intValue -= 1
                            currentOptionSelected.intValue = -1
                        },
                        border = BorderStroke(
                            1.dp,
                            color = AmozeshgamTheme.colors["primary"]!!
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AmozeshgamTheme.colors["background"]!!
                        )
                    ) {
                        Text(text = "قبلی", color = AmozeshgamTheme.colors["primary"]!!)
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    onClick = {
                        if (questionData.value!!.questions.size - 1 == currentIndex.intValue) {

                        } else {
                            currentOptionSelected.intValue = -1
                            currentIndex.intValue += 1
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmozeshgamTheme.colors["primary"]!!
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = if (questionData.value!!.questions.size - 1 == currentIndex.intValue) "ثبت" else "بعدی",
                        color = Color.White
                    )
                }
            }
        }
    }
}
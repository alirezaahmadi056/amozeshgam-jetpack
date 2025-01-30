package com.amozeshgam.amozeshgam.view.screens.home.roadMap.question

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAiQuestion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseRoadMapQuestion
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.roadMap.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewRoadMapQuestion(
    viewModel: QuestionViewModel = hiltViewModel(),
    navController: NavController,
) {
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
        mutableStateOf<ApiResponseRoadMapQuestion?>(null)
    }
    val screenSize = LocalConfiguration.current
    val roadMapId = remember {
        navController.currentBackStackEntry?.arguments?.getInt("roadMapId")
    }
    val numberOfQuestion = buildAnnotatedString {
        withStyle(style = SpanStyle(color = AmozeshgamTheme.colors["primary"]!!)) {
            append(currentIndex.intValue.toString())
        }
        append("/")
        withStyle(style = SpanStyle(color = AmozeshgamTheme.colors["primary"]!!)) {
            append(questionData.value?.questions?.size.toString())
        }
    }
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = questionData.value != null,
        worker = {
            questionData.value = viewModel.getRoadMapQuestion(id = roadMapId ?: 0)
            isLoading.value = false
            true
        }, onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = numberOfQuestion,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Text(text = "سوال")
                Spacer(modifier = Modifier.height(10.dp))
                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), state = sliderState
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.aspectRatio(16f / 9f)) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "",
                        textAlign = TextAlign.End
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = (screenSize.screenHeightDp / 2).dp)
                ) {
                    items(0) { index ->

                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (currentIndex.intValue != 0) {
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        onClick = {},
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
                    onClick = {},
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

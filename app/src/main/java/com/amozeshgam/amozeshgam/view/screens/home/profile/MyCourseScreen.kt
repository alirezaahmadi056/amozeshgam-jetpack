package com.amozeshgam.amozeshgam.view.screens.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyCourses
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.MyCourseItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.MyCourseViewModel

@Composable
fun ViewMyPackage(navController: NavController, viewModel: MyCourseViewModel = hiltViewModel()) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val myCoursesData = remember {
        mutableStateOf<ApiResponseMyCourses?>(null)
    }
    UiHandler.ContentWithScaffold(
        title = "دوره های من",
        onBackButtonClick = {
            navController.popBackStack()
        }
    ) {
        UiHandler.ContentWithLoading(
            loading = isLoading.value,
            ifForShowContent = myCoursesData.value != null,
            worker = {
                myCoursesData.value = viewModel.getMyCourseData().await()
                isLoading.value = false
                true
            }, onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            }
        ) {
            AmozeshgamTheme(
                darkTheme = UiHandler.themeState()
            ) {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .background(AmozeshgamTheme.colors["background"]!!)
                ) {
                    if (myCoursesData.value!!.courses.isEmpty()) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(250.dp),
                                painter = painterResource(id = R.drawable.my_course),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds
                            )
                            Text(
                                text = ".شما هنوز دوره ای را شروع نکردید",
                                color = AmozeshgamTheme.colors["textColor"]!!,
                                fontFamily = FontFamily(
                                    Font(R.font.yekan_bakh_regular)
                                ),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(it)
                        ) {
                            items(myCoursesData.value!!.courses.size) { index ->
                                MyCourseItem(
                                    courseImage = myCoursesData.value!!.courses[index].image,
                                    courseName = myCoursesData.value!!.courses[index].title
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

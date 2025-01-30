package com.amozeshgam.amozeshgam.view.screens.home.courses

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllCoursesList
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.CourseItem
import com.amozeshgam.amozeshgam.viewmodel.home.course.CoursesViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ViewCourses(navController: NavController, viewModel: CoursesViewModel = hiltViewModel()) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val packagesData = remember {
        mutableStateOf<List<ApiResponseAllCoursesList>?>(null)
    }
    val filteredPackageData = remember {
        mutableStateOf<List<ApiResponseAllCoursesList>?>(null)
    }
    val filterItem = viewModel.getFilterItemButton()
    UiHandler.ContentWithLoading(loading = isLoading.value, worker = {
        val response = viewModel.getPackagesData().await()
        packagesData.value = response?.courses
        filteredPackageData.value = packagesData.value
        isLoading.value = false
        true
    }, ifForShowContent = packagesData.value != null,onDismissRequestForDefaultErrorHandler = {
        navController.popBackStack()
    }) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(3) { index ->
                            Card(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp, vertical = 10.dp)
                                    .fillMaxHeight()
                                    .width(110.dp)
                                    .clickable {
                                        filterItem.forEach {
                                            it.selected.value = false
                                        }
                                        filterItem[index].selected.value = true
                                        filteredPackageData.value = packagesData.value
                                        filteredPackageData.value =
                                            viewModel.filterPackageWithFilteredButton(
                                                packageData = packagesData.value,
                                                filteredPackageData = filteredPackageData.value,
                                                indexFilter = index
                                            )
                                    },
                                colors = CardDefaults.cardColors(containerColor = if (filterItem[index].selected.value) filterItem[index].selectedBackgroundColor.invoke() else filterItem[index].unSelectedBackgroundColor.invoke()),
                                border = BorderStroke(
                                    1.dp,
                                    color = if (filterItem[index].selected.value) filterItem[index].selectedBorderColor.invoke() else filterItem[index].unSelectedBorderColor.invoke()
                                ),
                                shape = RoundedCornerShape(20.dp),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center

                                ) {
                                    Image(
                                        painter = painterResource(id = if (filterItem[index].selected.value) filterItem[index].selectedIcon else filterItem[index].unSelectedIcon),
                                        contentDescription = null
                                    )
                                    Text(
                                        modifier = Modifier.padding(horizontal = 7.dp),
                                        text = filterItem[index].title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = if (filterItem[index].selected.value) Color.White else filterItem[index].unSelectedTextColor.invoke()
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    LazyVerticalGrid(
                        modifier = Modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp),
                        columns = GridCells.Fixed(2)
                    ) {
                        items(filteredPackageData.value!!.size) { index ->
                            CourseItem(
                                imageUrl = filteredPackageData.value!![index].image,
                                packageName = filteredPackageData.value!![index].title,
                                time = filteredPackageData.value!![index].time,
                                teacher = filteredPackageData.value!![index].teacher,
                                onClick = {
                                    navController.navigate("${NavigationHandler.SingleCourseScreen.route}/${packagesData.value!![index].id}")
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}


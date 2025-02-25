package com.amozeshgam.amozeshgam.view.screens.home

import android.icu.text.IDNA.Info
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseInformation
import com.amozeshgam.amozeshgam.view.items.InformationItem
import com.amozeshgam.amozeshgam.view.items.InformationPackageItem
import com.amozeshgam.amozeshgam.view.items.InformationTeamItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.InformationViewModel
import okhttp3.internal.cookieToString
import kotlin.math.max

@Composable
fun ViewInformation(
    viewModel: InformationViewModel = hiltViewModel(),
    navController: NavController,
) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val informationData = remember {
        mutableStateOf<ApiResponseInformation?>(null)
    }
    UiHandler.ContentWithLoading(loading = isLoading.value, worker = {
        informationData.value = viewModel.getInformationData().await()
        isLoading.value = false
        true
    }, ifForShowContent = informationData.value != null, onDismissRequestForDefaultErrorHandler = {
        navController.popBackStack()
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "علیرضا احمدی",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_black)),
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    repeat(informationData.value!!.data.role.size) { index ->
                        UiHandler.AnythingRow(
                            modifier = Modifier.padding(vertical = 5.dp),
                            itemOne = {
                                Text(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    text = informationData.value!!.data.role[index],
                                    color = AmozeshgamTheme.colors["textColor"]!!,
                                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                                )
                            },
                            itemTwo = {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.ic_tick),
                                    contentDescription = null
                                )
                            })
                    }
                }
                AsyncImage(
                    modifier = Modifier.size(180.dp),
                    model = informationData.value!!.data.avatar,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(10.dp)
                    .heightIn(max = LocalConfiguration.current.screenHeightDp.dp),
                columns = GridCells.Fixed(2)
            ) {
                item {
                    InformationItem(
                        image = R.drawable.ic_feedback,
                        text = "امتیاز دانشجویان",
                        title = "${informationData.value!!.data.rate} از 5"
                    )
                }
                item {
                    InformationItem(
                        image = R.drawable.ic_student_count,
                        text = "تعداد دانشجو",
                        title = "${informationData.value!!.data.studentCount} نفر "
                    )
                }
                item {
                    InformationItem(
                        image = R.drawable.ic_clock,
                        text = "ساعت آموزش",
                        title = "${informationData.value!!.data.totalTeachDuration} ساعت"
                    )
                }
                item {
                    InformationItem(
                        image = R.drawable.ic_clock,
                        text = "تعداد دوره ها",
                        title = "${informationData.value!!.data.courseCount} عدد "
                    )
                }
            }
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp),
                text = "دوره های آموزشی",
                fontFamily = FontFamily(Font(R.font.yekan_bakh_black)),
                fontSize = 20.sp,
                textAlign = TextAlign.Right,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                ) {
                    items(informationData.value!!.data.courses.size) { index ->
                        InformationPackageItem(
                            image = informationData.value!!.data.courses[index].thumbnail,
                            name = informationData.value!!.data.courses[index].title
                        )
                    }
                }
            }
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.End),
                text = "اعضای تیم",
                fontFamily = FontFamily(Font(R.font.yekan_bakh_black)),
                fontSize = 20.sp,
                textAlign = TextAlign.Right,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
            LazyVerticalGrid(
                modifier = Modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(informationData.value!!.data.teamMembers.size) { index ->
                    InformationTeamItem(
                        name = informationData.value!!.data.teamMembers[index].name,
                        image = informationData.value!!.data.teamMembers[index].avatar,
                        duty = informationData.value!!.data.teamMembers[index].role
                    )
                }
            }
        }
    }
}

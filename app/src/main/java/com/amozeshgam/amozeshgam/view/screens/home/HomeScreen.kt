package com.amozeshgam.amozeshgam.view.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseHomeData
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.handler.openLink
import com.amozeshgam.amozeshgam.view.items.CourseItem
import com.amozeshgam.amozeshgam.view.items.FieldAndSubFieldItem
import com.amozeshgam.amozeshgam.view.items.PaymentItem
import com.amozeshgam.amozeshgam.view.items.PodcastItem
import com.amozeshgam.amozeshgam.view.items.StoryItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.HomeViewModel

@Composable
fun ViewHome(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val loading = remember {
        mutableStateOf(true)
    }
    val homeData = remember {
        mutableStateOf<ApiResponseHomeData?>(null)
    }
    val context = LocalContext.current
    UiHandler.ContentWithLoading(loading = loading.value, worker = {
        homeData.value = viewModel.getHomeData().await()
        loading.value = false
        true
    }, ifForShowContent = homeData.value != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(AmozeshgamTheme.colors["background"]!!)
        ) {
            CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp)
                ) {
                    items(homeData.value!!.storyBanner.size, key = {
                        it
                    }) { index ->
                        StoryItem(
                            text = homeData.value!!.storyBanner[index].title, onClick = {
                                navController.navigate("${NavigationScreenHandler.StoryScreen.route}/${homeData.value!!.storyBanner[index].id}")
                            }, imageUrl = homeData.value!!.storyBanner[index].media
                        )
                    }
                }

            }
            if (homeData.value?.banners?.getOrNull(0) != null) {
                AsyncImage(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(2.79f / 1f)
                        .clickable {
                            navController.navigate(NavigationScreenHandler.GuidancePurchaseScreen.route)
                        }, model = homeData.value!!.banners[0].image, contentDescription = null
                )
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.ic_question),
                    contentDescription = null
                )
                Text(

                    text = "می خوای متخصص چی بشی",
                    fontSize = 25.sp,
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["black"],
                )
            }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                LazyVerticalGrid(
                    modifier = Modifier.heightIn(max = LocalConfiguration.current.screenHeightDp.dp),
                    columns = GridCells.Fixed(2)
                ) {
                    items(homeData.value!!.fields.size, key = {
                        it
                    }) { index ->
                        FieldAndSubFieldItem(
                            modifier = Modifier.padding(10.dp),
                            imageUrl = homeData.value!!.fields[index].image,
                            text = homeData.value!!.fields[index].title
                        ) {
                            navController.navigate(NavigationScreenHandler.FieldScreen.route + "/" + homeData.value!!.fields[index].id)
                        }
                    }
                }
            }
            CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(AmozeshgamTheme.colors["paymentBackgroundColor"]!!)
                        .padding(10.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        Column(
                            modifier = Modifier.width(120.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "مسیر دقیق رسیدن به درآمد پایدار",
                                fontSize = 24.sp,
                                color = AmozeshgamTheme.colors["paymentTextColor"]!!,
                                textAlign = TextAlign.Center,
                                fontFamily = AmozeshgamTheme.fonts["black"]
                            )
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .align(Alignment.CenterHorizontally),
                                painter = painterResource(id = R.drawable.ic_dolar),
                                contentDescription = null,
                            )
                        }
                    }
                    items(homeData.value!!.roadMap.size, key = { it }) { index ->
                        PaymentItem(
                            imageUrl = homeData.value!!.roadMap[index].image,
                            text = homeData.value!!.roadMap[index].title
                        )
                    }
                }
            }
            if (homeData.value?.banners?.getOrNull(1) != null) {
                AsyncImage(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(2.79f / 1f)
                        .clickable {
                            context.openLink(homeData.value!!.banners[1].link)
                        },
                    model = homeData.value!!.banners[1].image,
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UiHandler.AnythingRow(modifier = Modifier.clickable {
                    navController.navigate(NavigationScreenHandler.AllCoursesScreen.route)
                }, itemOne = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = null,
                        tint = AmozeshgamTheme.colors["textColor"]!!
                    )
                }, itemTwo = {
                    Text(
                        text = "مشاهده ی همه",
                        color = AmozeshgamTheme.colors["textColor"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                })
                Text(
                    text = "محبوب ترین دوره ها",
                    fontSize = 25.sp,
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontWeight = FontWeight.Black,
                    fontFamily = AmozeshgamTheme.fonts["regular"]

                )
            }
            CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(homeData.value!!.courses.size) { index ->
                        CourseItem(imageUrl = homeData.value!!.courses[index].image,
                            packageName = homeData.value!!.courses[index].title,
                            time = homeData.value!!.courses[index].time,
                            teacher = homeData.value!!.courses[index].teacher,
                            onClick = {
                                navController.navigate("${NavigationScreenHandler.SingleCourseScreen.route}/${homeData.value!!.courses[index].id}")
                            })
                    }
                }
            }
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp),
                text = "از کی یاد می گیریم؟",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = AmozeshgamTheme.fonts["black"],
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
            )
            AsyncImage(
                modifier = Modifier
                    .padding(10.dp)
                    .aspectRatio(2.79f / 1.25f)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate(NavigationScreenHandler.InformationScreen.route)
                    },
                model = homeData.value!!.manager.image,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UiHandler.AnythingRow(modifier = Modifier.clickable {
                    navController.navigate(NavigationScreenHandler.AllPodcastScreen.route)
                }, itemOne = {
                    Icon(
                        contentDescription = null,
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        tint = AmozeshgamTheme.colors["textColor"]!!
                    )
                }, itemTwo = {
                    Text(
                        text = "مشاهده ی همه",
                        color = AmozeshgamTheme.colors["textColor"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]

                    )
                })
                Text(
                    text = "پادکست ها",
                    fontSize = 25.sp,
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                    fontWeight = FontWeight.Black
                )
            }
            CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(homeData.value!!.podcasts.size, key = {
                        it
                    }) { index ->
                        PodcastItem(
                            onClick = {
                                navController.navigate("${NavigationScreenHandler.PodcastPlayerScreen.route}/${homeData.value!!.podcasts[index].id}")
                            },
                            imageUrl = homeData.value!!.podcasts[index].image,
                            text = homeData.value!!.podcasts[index].title,
                            speech = homeData.value!!.podcasts[index].speaker
                        )
                    }
                }
            }
            if (homeData.value?.banners?.getOrNull(2) != null) {
                AsyncImage(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(2.79f / 1f)
                        .clickable {
                            context.openLink(homeData.value!!.banners[2].link)
                        }, model = homeData.value!!.banners[2].image, contentDescription = null
                )
            }
            if (homeData.value?.banners?.getOrNull(3) != null) {
                AsyncImage(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(2.79f / 1f)
                        .clickable {
                            context.openLink(homeData.value!!.banners[3].link)
                        }, model = homeData.value!!.banners[3].image, contentDescription = null
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp),
                text = "شبکه های ما",
                fontSize = 25.sp,
                fontWeight = FontWeight.Black,
                color = AmozeshgamTheme.colors["textColor"]!!,
                fontFamily = AmozeshgamTheme.fonts["black"]
            )
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp, vertical = 0.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(homeData.value!!.socials.size) { index ->
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                            .height(70.dp)
                            .padding(5.dp),
                        onClick = {
                            context.openLink(homeData.value!!.socials[index].link)
                        },
                        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxHeight(),
                            model = homeData.value!!.socials[index].image,
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null
                        )
                    }
                }
            }

        }
    }
}
package com.amozeshgam.amozeshgam.view.screens.home.roadMap.field

import android.content.Intent
import android.icu.text.DecimalFormat
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleSubField
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.RoadMapAbilityItem
import com.amozeshgam.amozeshgam.view.items.RoadMapSoftwareCanBuild
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.roadMap.SubFieldViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ViewSingleSubField(
    navController: NavController,
    viewModel: SubFieldViewModel = hiltViewModel(),
) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val subFieldData = remember {
        mutableStateOf<ApiResponseSingleSubField?>(null)
    }
    val subFieldId = remember {
        navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    }

    val context = LocalContext.current

    UiHandler.ContentWithLoading(loading = isLoading.value,
        ifForShowContent = subFieldData.value != null,
        worker = {
            subFieldData.value = viewModel.getSubFieldData(id = subFieldId).await()
            isLoading.value = false
            true
        },
        onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }) {
        AmozeshgamTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AmozeshgamTheme.colors["background"]!!)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = subFieldData.value!!.title,
                        fontSize = 25.sp,
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .aspectRatio(16f / 9f)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = null,
                            contentDescription = null
                        )
                    }
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = ":پس از گذراندن این مسیر یادگیری تو میتونی",
                        fontSize = 20.sp,
                        fontFamily = AmozeshgamTheme.fonts["regular"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp)
                    ) {
                        items(subFieldData.value!!.futures.size) { index ->
                            RoadMapAbilityItem(
                                image = subFieldData.value!!.futures[index].image,
                                text = subFieldData.value!!.futures[index].text,
                                itemNumber = index + 1
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "با مسیر پیش رو میتونی نرم افزارهای زیر رو بسازی",
                        fontSize = 18.sp,
                        fontFamily = AmozeshgamTheme.fonts["regular"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    FlowRow(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(subFieldData.value!!.software.size) { index ->
                            RoadMapSoftwareCanBuild(
                                image = subFieldData.value!!.software[index].image,
                                text = subFieldData.value!!.software[index].text
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "ویژگی های طرح",
                        fontSize = 25.sp,
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        items(subFieldData.value!!.features.size) { index ->
                            UiHandler.AnythingRow(itemOne = {
                                Text(
                                    text = subFieldData.value!!.features[index],
                                    fontFamily = AmozeshgamTheme.fonts["regular"]
                                )
                            }, itemTwo = {
                                Image(
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    painter = painterResource(R.drawable.ic_tick),
                                    contentDescription = null
                                )
                            })
                        }
                    }
                    AsyncImage(
                        modifier = Modifier
                            .aspectRatio(2.79f / 1f)
                            .clickable {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(subFieldData.value!!.banner.link)
                                    )
                                )
                            },
                        model = subFieldData.value!!.banner.banner,
                        contentDescription = null
                    )
                }
                //check has purchase
                if (true) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(70.dp)
                            .background(AmozeshgamTheme.colors["background"]!!),
                        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(0.5f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = DecimalFormat(",000").format(subFieldData.value!!.price)
                                )
                                Text(
                                    text = "تومان"
                                )
                            }
                            Button(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .weight(1f)
                                    .fillMaxHeight(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AmozeshgamTheme.colors["primary"]!!,
                                ),
                                shape = RoundedCornerShape(10.dp),
                                onClick = {}
                            ) {
                                Text(
                                    text = "اضافه کردن به سبد خرید",
                                    color = Color.White
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_cart),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
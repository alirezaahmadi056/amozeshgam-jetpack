package com.amozeshgam.amozeshgam.view.screens.home.roadMap.guidance

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetFieldPackage
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.RoadMapSoftwareCanBuild
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.roadMap.GuidanceViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ViewGuidancePurchase(
    navController: NavController,
    viewModel: GuidanceViewModel = hiltViewModel()
) {

    val isLoading = remember {
        mutableStateOf(true)
    }
    val fieldData = remember {
        mutableStateOf<ApiResponseGetFieldPackage?>(null)
    }
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = fieldData.value != null,
        worker = {
            viewModel.getFieldPackageData().await()
                .also {
                    fieldData.value = it
                }
            isLoading.value = false
            true
        },
        onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }
    ) {
        AmozeshgamTheme {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .aspectRatio(16f / 9f),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Red)

                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = null,
                            contentDescription = null
                        )
                    }
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(vertical = 5.dp, horizontal = 10.dp),
                        text = "نمیدونی از کجا شروع کنی؟",
                        fontSize = 25.sp,
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        textAlign = TextAlign.Right,
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(vertical = 5.dp, horizontal = 10.dp),
                        text = fieldData.value!!.description,
                        fontFamily = AmozeshgamTheme.fonts["regular"],
                        textAlign = TextAlign.Right,
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(10.dp),
                        text = "حوزه هایی که بررسی میکنیم",
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        textAlign = TextAlign.Right,
                        fontSize = 25.sp,
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center
                    ) {
                        repeat(fieldData.value!!.fields.size) { index ->
                            RoadMapSoftwareCanBuild(
                                image = fieldData.value!!.fields[index].image,
                                text = fieldData.value!!.fields[index].title
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.BottomCenter)
                        .shadow(10.dp, ambientColor = AmozeshgamTheme.colors["shadowColor"]!!, spotColor = AmozeshgamTheme.colors["shadowColor"]!!),
                    colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
                    elevation = CardDefaults.cardElevation(10.dp),
                ) {
                    if (!fieldData.value!!.isBuy) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .weight(1f),
                            ) {
                                Row {
                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = 2.dp),
                                        text = DecimalFormat(",000").format(fieldData.value!!.price),
                                        color = AmozeshgamTheme.colors["textColor"]!!,
                                        fontFamily = AmozeshgamTheme.fonts["regular"],
                                        fontSize = 16.sp
                                    )
                                    if (fieldData.value!!.percent > 0) {
                                        Text(
                                            modifier = Modifier
                                                .padding(horizontal = 2.dp),
                                            text = DecimalFormat(",000").format(fieldData.value!!.price),
                                            color = AmozeshgamTheme.colors["textColor"]!!,
                                            fontFamily = AmozeshgamTheme.fonts["regular"],
                                            fontSize = 16.sp
                                        )
                                        Box(
                                            modifier = Modifier
                                                .padding(horizontal = 2.dp)
                                                .clip(RoundedCornerShape(5.dp))
                                                .background(AmozeshgamTheme.colors["primary"]!!)
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(2.dp),
                                                text = fieldData.value!!.percent.toString() + "%",
                                                color = Color.White,
                                                fontSize = 15.sp,
                                                fontFamily = AmozeshgamTheme.fonts["regular"]
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = "تومان",
                                    color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                                    fontFamily = AmozeshgamTheme.fonts["regular"]
                                )
                            }
                            Button(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxHeight(),
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AmozeshgamTheme.colors["primary"]!!
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    text = "ادامه",
                                    color = Color.White,
                                    fontFamily = AmozeshgamTheme.fonts["regular"],
                                )
                            }
                        }
                    } else {
                        Button(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxSize(),
                            onClick = {

                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AmozeshgamTheme.colors["primary"]!!
                            )
                        ) {
                            Text(
                                text = "!شروع",
                                fontFamily = AmozeshgamTheme.fonts["regular"],
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
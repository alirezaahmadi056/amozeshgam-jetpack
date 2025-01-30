package com.amozeshgam.amozeshgam.view.screens.home.roadMap.job

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ViewJobsPurchase(navController: NavController) {
    AmozeshgamTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(16f / 9f)
                        .background(Color.Red),
                    shape = RoundedCornerShape(10.dp)

                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(), model = null, contentDescription = null
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "نمیدونی از کجا شروع کنی؟",
                    fontSize = 20.sp,
                    fontFamily = AmozeshgamTheme.fonts["bold"],
                    textAlign = TextAlign.Right
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "",
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                    textAlign = TextAlign.Right
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "حوزه هایی که بررسی میکنیم",
                    fontFamily = AmozeshgamTheme.fonts["bold"],
                    textAlign = TextAlign.Right,
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center
                ) { }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .shadow(10.dp)
            ) {
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
                                text = "price"
                            )
                            //check course have a discount
                            if (true) {
                                Text(
                                    text = "real price"
                                )
                                Box(
                                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                ) {
                                    Text(
                                        text = "discount"
                                    )
                                }
                            }
                        }
                        Text(
                            text = "تومان"
                        )
                    }
                }
            }
        }
    }
}
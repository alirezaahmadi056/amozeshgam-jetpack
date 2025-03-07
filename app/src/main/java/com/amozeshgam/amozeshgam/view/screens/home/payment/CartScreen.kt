package com.amozeshgam.amozeshgam.view.screens.home.payment

import android.icu.text.DecimalFormat
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseCart
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseCartCoursesAndRoadMapData
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.CartItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.payment.CartViewModel

@Composable
fun ViewCart(viewModel: CartViewModel = hiltViewModel(), navController: NavController) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val cartData = remember {
        mutableStateOf<ApiResponseCart?>(null)
    }
    val roadMaps = remember {
        mutableStateListOf<ApiResponseCartCoursesAndRoadMapData>()
    }
    val courses = remember {
        mutableStateListOf<ApiResponseCartCoursesAndRoadMapData>()
    }
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    val removeIndex = remember {
        mutableIntStateOf(-1)
    }
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = cartData.value != null,
        worker = {
            cartData.value = viewModel.getMyCart().await().also {
                roadMaps.addAll(it?.roadMaps ?: listOf())
                courses.addAll(it?.courses ?: listOf())
            }

            isLoading.value = false
            true
        },
        onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AmozeshgamTheme.colors["background"]!!),
        ) {
            if (courses.isNotEmpty() || roadMaps.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    items(roadMaps.size) { index: Int ->
                        CartItem(
                            image = roadMaps[index].image,
                            price = roadMaps[index].price,
                            name = roadMaps[index].title,
                            removeLoading = removeIndex.intValue == index
                        ) {
                            if (removeIndex.intValue != index) {
                                viewModel.removeRoadMapFromMyCart(roadMaps[index].id)
                                removeIndex.intValue = index
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .padding(horizontal = 10.dp, vertical = 3.dp),
                            color = AmozeshgamTheme.colors["borderColor"]!!
                        )
                    }
                    items(courses.size) { index: Int ->
                        CartItem(
                            image = courses[index].image,
                            price = courses[index].price,
                            name = courses[index].title,
                            removeLoading = removeIndex.intValue == index
                        ) {
                            if (removeIndex.intValue != index) {
                                viewModel.removeCourseFromMyCart(courses[index].id)
                                removeIndex.intValue = index
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .padding(horizontal = 10.dp, vertical = 3.dp),
                            color = AmozeshgamTheme.colors["borderColor"]!!
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .shadow(
                            20.dp,
                            ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                            spotColor = AmozeshgamTheme.colors["shadowColor"]!!
                        )
                        .align(Alignment.BottomCenter),
                    shape = RoundedCornerShape(0),
                    colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
                    elevation = CardDefaults.cardElevation(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "جمع سبد خرید",
                                color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                                fontSize = 10.sp,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                            Text(
                                text = DecimalFormat(",000").format(cartData.value!!.finalPrice),
                                fontSize = 22.sp,
                                color = AmozeshgamTheme.colors["textColor"]!!,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                            Text(
                                text = "تومان",
                                color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                                fontSize = 11.sp,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                        }
                        Button(
                            modifier = Modifier
                                .padding(10.dp)
                                .border(
                                    2.dp,
                                    AmozeshgamTheme.colors["primary"]!!,
                                    RoundedCornerShape(10.dp)
                                ),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["background"]!!),
                            onClick = {
                                navController.navigate(NavigationScreenHandler.PaymentScreen.route)
                            }) {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                text = "ادامه و ثبت سفارش",
                                color = AmozeshgamTheme.colors["primary"]!!,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 40.dp)
                            .aspectRatio(0.5f / 0.5f),
                        painter = painterResource(R.drawable.ic_empty_transaction),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        text = ".لیست سفارشات شما خالی است",
                        fontFamily = AmozeshgamTheme.fonts["regular"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                }
            }
        }
        LaunchedEffect(Unit) {
            viewModel.removeCourseFromMyCartIsSuccess.observe(lifecycle) {
                if (it) {
                    courses.removeAt(removeIndex.intValue)
                    removeIndex.intValue = -1
                    Toast.makeText(context, "با موفقیت حذف شد", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "خطا در حذف", Toast.LENGTH_SHORT).show()
                }

            }
            viewModel.removeRoadMapFromMyCartIsSuccess.observe(lifecycle) {
                if (it) {
                    roadMaps.removeAt(removeIndex.intValue)
                    removeIndex.intValue = -1
                    Toast.makeText(context, "با موفقیت حذف شد", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "خطا در حذف", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
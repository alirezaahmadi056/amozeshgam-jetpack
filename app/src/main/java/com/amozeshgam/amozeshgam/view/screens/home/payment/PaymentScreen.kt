package com.amozeshgam.amozeshgam.view.screens.home.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponsePayment
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.payment.PaymentViewModel
import java.text.DecimalFormat

@Composable
fun ViewPayment(viewModel: PaymentViewModel = hiltViewModel(), navController: NavController) {
    val finalPrice = remember {
        mutableStateOf("")
    }
    val wallet = remember {
        mutableIntStateOf(0)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val paymentData = remember {
        mutableStateOf<ApiResponsePayment?>(null)
    }
    val discount = remember {
        mutableIntStateOf(0)
    }
    val discountText = remember {
        mutableStateOf("")
    }
    val paymentGateway = remember {
        mutableStateOf(true)
    }
    UiHandler.ContentWithLoading(loading = isLoading.value, ifForShowContent = true, worker = {
        paymentData.value = viewModel.getPaymentData().await()
        finalPrice.value = (paymentData.value?.total ?: 0).toString()
        wallet.intValue = (paymentData.value?.wallet ?: 0).toString().toInt()
        isLoading.value = false
        true
    }, onDismissRequestForDefaultErrorHandler = {
        navController.popBackStack()
    }) {
        AmozeshgamTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                2.dp,
                                color = AmozeshgamTheme.colors["borderColor"]!!,
                                shape = RoundedCornerShape(10.dp)
                            ),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.End),
                                text = "جزئیات پرداخت",
                                fontSize = 12.sp,
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                            if (paymentData.value!!.courseCount > 0) {
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                        Text(
                                            text = "${DecimalFormat(",000").format(paymentData.value!!.coursesPrice)}تومان",
                                            color = AmozeshgamTheme.colors["textColor"]!!,
                                            fontFamily = AmozeshgamTheme.fonts["regular"],
                                            fontSize = 19.sp
                                        )
                                    }
                                    Text(
                                        text = "هزینه دوره ها(${paymentData.value!!.courseCount})",
                                        color = AmozeshgamTheme.colors["textColor"]!!,
                                        fontSize = 19.sp,
                                        fontFamily = AmozeshgamTheme.fonts["regular"]
                                    )
                                }
                            }
                            if (paymentData.value!!.roadmapCount > 0) {
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                        Text(
                                            text = "${DecimalFormat(",000").format(paymentData.value!!.roadmapPrice)}تومان",
                                            color = AmozeshgamTheme.colors["textColor"]!!,
                                            fontFamily = AmozeshgamTheme.fonts["regular"],
                                            fontSize = 19.sp
                                        )
                                    }
                                    Text(
                                        text = "هزینه مسیرهای یادگیری(${paymentData.value!!.roadmapCount})",
                                        color = AmozeshgamTheme.colors["textColor"]!!,
                                        fontFamily = AmozeshgamTheme.fonts["regular"],
                                        fontSize = 19.sp
                                    )
                                }
                            }
                            if (discount.intValue > 0) {
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                        Text(
                                            text = "${DecimalFormat(",000").format(discount.intValue)}تومان",
                                            color = AmozeshgamTheme.colors["textColor"]!!,
                                            fontFamily = AmozeshgamTheme.fonts["regular"],
                                            fontSize = 19.sp
                                        )
                                    }
                                    Text(
                                        text = "مجموع تخفیف ها",
                                        color = AmozeshgamTheme.colors["errorColor"]!!,
                                        fontFamily = AmozeshgamTheme.fonts["regular"],
                                        fontSize = 19.sp
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                2.dp,
                                color = AmozeshgamTheme.colors["borderColor"]!!,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.End),
                                text = "روش پرداخت",
                                fontSize = 12.sp,
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                            if (discount.intValue > 0) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_delete),
                                            contentDescription = null
                                        )
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.End
                                    ) {

                                    }
                                }
                            } else {
                                UiHandler.OutLineTextField(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    label = {
                                        Text(text = "افزودن کد تخفیف")
                                    },
                                    value = discountText.value,
                                    onValueChange = {
                                        discountText.value = it
                                    },
                                    trailingIcon = {
                                        Button(
                                            modifier = Modifier.padding(5.dp),
                                            enabled = discountText.value.isNotEmpty(),
                                            onClick = { /*TODO*/ },
                                            shape = RoundedCornerShape(5.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["primary"]!!)
                                        ) {
                                            Text(text = "اعمال کد تخفیف", color = Color.White)
                                        }
                                    }
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(210.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                2.dp,
                                color = AmozeshgamTheme.colors["borderColor"]!!,
                                shape = RoundedCornerShape(10.dp)
                            ),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.End),
                                text = "روش پرداخت",
                                fontSize = 12.sp,
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RadioButton(
                                    selected = paymentGateway.value,
                                    onClick = {
                                        paymentGateway.value = true
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = AmozeshgamTheme.colors["primary"]!!)
                                )
                                UiHandler.AnythingRow(itemOne = {
                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "پرداخت انلاین",
                                            fontFamily = AmozeshgamTheme.fonts["regular"],
                                            fontSize = 17.sp,
                                            color = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                        Text(
                                            text = "درگاه پرداخت",
                                            color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                                        )
                                    }
                                }, itemTwo = {
                                    Image(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .size(50.dp),
                                        painter = painterResource(id = R.drawable.ic_cart_port),
                                        contentDescription = null
                                    )
                                })
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .fillMaxWidth(),
                                thickness = 2.dp,
                                color = AmozeshgamTheme.colors["borderColor"]!!
                            )
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RadioButton(
                                    selected = !paymentGateway.value,
                                    onClick = {
                                        paymentGateway.value = false
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = AmozeshgamTheme.colors["primary"]!!)
                                )
                                UiHandler.AnythingRow(itemOne = {
                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "پرداخت با کیف پول آموزشگام",
                                            fontFamily = AmozeshgamTheme.fonts["regular"],
                                            fontSize = 17.sp,
                                            color = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                            Text(
                                                text = "موجوده : ${
                                                    DecimalFormat(",000").format(
                                                        wallet.intValue
                                                    )
                                                } تومان",
                                                color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                                            )
                                        }
                                    }
                                }, itemTwo = {
                                    Image(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .size(50.dp),
                                        painter = painterResource(id = R.drawable.ic_app_logo),
                                        contentDescription = null
                                    )
                                })
                            }
                        }
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
                                text = DecimalFormat(",000").format(finalPrice.value.toInt()),
                                color = AmozeshgamTheme.colors["textColor"]!!,
                                fontFamily = AmozeshgamTheme.fonts["regular"],
                                fontSize = 22.sp
                            )
                            Text(
                                text = "تومان",
                                color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                                fontSize = 11.sp,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                        }
                        Button(modifier = Modifier.padding(10.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["primary"]!!),
                            onClick = {}) {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                text = "پرداخت و شروع یادگیری",
                                color = Color.White,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                        }
                    }
                }
            }
        }
    }
}
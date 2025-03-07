package com.amozeshgam.amozeshgam.view.screens.home.profile

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetProfile
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.ProfileViewModel

@Composable
fun ViewProfile(viewModel: ProfileViewModel = hiltViewModel(), navController: NavController) {
    val item = viewModel.getProfileItems()
    val showUiSettingDialog = remember {
        mutableStateOf(false)
    }
    val enabledNotification = viewModel.enabledAmozeshgamNotification.collectAsState()

    val userData = remember {
        mutableStateOf<ApiResponseGetProfile?>(null)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val showWalletCharge = remember {
        mutableStateOf(false)
    }
    UiHandler.ContentWithLoading(
        ifForShowContent = userData.value != null,
        loading = isLoading.value,
        worker = {
            viewModel.getCurrentNotificationState()
            userData.value = viewModel.getUserData().await()
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
                    .fillMaxSize()
                    .background(AmozeshgamTheme.colors["background"]!!)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(AmozeshgamTheme.colors["background"]!!)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    AsyncImage(
                        modifier = Modifier
                            .size(180.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(100.dp)),
                        model = userData.value!!.avatar,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = userData.value!!.username,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    UiHandler.AnythingRow(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                navController.navigate(
                                    "${NavigationScreenHandler.ProfileEditorScreen.route}/${userData.value!!.username}/${userData.value!!.phone}/${userData.value!!.birthday ?: ""}/${userData.value!!.email ?: ""}/${
                                        Uri.encode(
                                            userData.value!!.avatar
                                        )
                                    }"
                                )
                            },
                        itemOne = {
                            Text(
                                text = "ویرایش اطلاعات کاربری",
                                color = AmozeshgamTheme.colors["primary"]!!
                            )
                        },
                        itemTwo = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = null,
                                tint = AmozeshgamTheme.colors["primary"]!!
                            )
                        })
                    UiHandler.WalletCardProfile(
                        userData.value!!.walletInventory.toInt(),
                        onTransactionClick = { navController.navigate(NavigationScreenHandler.MyTransactionsScreen.route) },
                        onChargingClick = { showWalletCharge.value = true })
                    repeat(item.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .fillMaxWidth()
                                .clickable {
                                    if (item[index].route == "dialog") {
                                        showUiSettingDialog.value = true
                                    } else {
                                        navController.navigate(item[index].route)
                                    }
                                }
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Row(
                                        modifier = Modifier.weight(0.9f),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_arrow_left),
                                            contentDescription = null,
                                            tint = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                        Text(
                                            modifier = Modifier.padding(5.dp),
                                            text = item[index].label,
                                            color = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                    }
                                    Image(
                                        modifier = Modifier.weight(0.1f),
                                        painter = painterResource(id = item[index].icon),
                                        contentDescription = null
                                    )
                                }
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 28.dp, top = 5.dp, bottom = 5.dp),
                                    thickness = 1.dp,
                                    color = AmozeshgamTheme.colors["borderColor"]!!
                                )
                            }
                        }
                    }
                    if (userData.value!!.cups.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(12.dp),
                            text = "عناوین کسب شده",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = AmozeshgamTheme.colors["textColor"]!!
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = (LocalConfiguration.current.screenHeightDp / 2).dp)
                        ) {
                            items(userData.value!!.cups.size) { index ->
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    AsyncImage(
                                        model = userData.value!!.cups[index].image,
                                        contentDescription = null
                                    )
                                    Text(
                                        text = userData.value!!.cups[index].title,
                                        fontFamily = AmozeshgamTheme.fonts["regular"],
                                        color = AmozeshgamTheme.colors["textColor"]!!
                                    )
                                }
                            }
                        }
                    }
                }
                if (showUiSettingDialog.value) {
                    UiHandler.CustomDialog(
                        onDismiss = {
                            showUiSettingDialog.value = false
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 170.dp)
                                .padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "تنظیمات",
                                textAlign = TextAlign.Center,
                                color = AmozeshgamTheme.colors["textColor"]!!,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .weight(1f)
                                        .height(100.dp),
                                    colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors[if (GlobalUiModel.uiTheme.intValue == GlobalUiModel.SYSTEM_THEME_CODE) "primary" else "itemColor"]!!),
                                    onClick = {
                                        viewModel.changeTheme(GlobalUiModel.SYSTEM_THEME_CODE)
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxSize()
                                    ) {
                                        Column(
                                            modifier = Modifier.align(Alignment.Center),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "A",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 20.sp,
                                                color = if (GlobalUiModel.uiTheme.intValue == GlobalUiModel.SYSTEM_THEME_CODE) Color.White else AmozeshgamTheme.colors["textColor"]!!
                                            )
                                            Text(
                                                text = "اتوماتیک",
                                                color = if (GlobalUiModel.uiTheme.intValue == GlobalUiModel.SYSTEM_THEME_CODE) Color.White else AmozeshgamTheme.colors["textColor"]!!
                                            )
                                        }
                                    }
                                }
                                Card(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .weight(1f)
                                        .height(100.dp),
                                    colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors[if (GlobalUiModel.uiTheme.intValue == GlobalUiModel.DARK_CODE) "primary" else "itemColor"]!!),
                                    onClick = {
                                        viewModel.changeTheme(GlobalUiModel.DARK_CODE)
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxSize()
                                    ) {
                                        Column(
                                            modifier = Modifier.align(Alignment.Center),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_dark),
                                                contentDescription = null,
                                                tint = AmozeshgamTheme.colors["textColor"]!!
                                            )
                                            Text(
                                                text = "تیره",
                                                color = AmozeshgamTheme.colors["textColor"]!!
                                            )
                                        }
                                    }
                                }
                                Card(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .weight(1f)
                                        .height(100.dp),
                                    colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors[if (GlobalUiModel.uiTheme.intValue == GlobalUiModel.LIGHT_CODE) "primary" else "itemColor"]!!),
                                    onClick = {
                                        viewModel.changeTheme(GlobalUiModel.LIGHT_CODE)
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxSize()
                                    ) {
                                        Column(
                                            modifier = Modifier.align(Alignment.Center),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.ic_light),
                                                contentDescription = null,
                                                tint = if (GlobalUiModel.uiTheme.intValue == GlobalUiModel.LIGHT_CODE) Color.White else AmozeshgamTheme.colors["textColor"]!!
                                            )
                                            Text(
                                                text = "روشن",
                                                color = if (GlobalUiModel.uiTheme.intValue == GlobalUiModel.LIGHT_CODE) Color.White else AmozeshgamTheme.colors["textColor"]!!
                                            )
                                        }
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Switch(
                                    checked = enabledNotification.value,
                                    onCheckedChange = {
                                        viewModel.changeNotificationState()
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = AmozeshgamTheme.colors["primary"]!!,
                                        checkedTrackColor = AmozeshgamTheme.colors["itemColor"]!!.copy(
                                            alpha = 1f
                                        )
                                    ),
                                    thumbContent = {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clip(RoundedCornerShape(100))
                                        )
                                    }
                                )
                                Text(
                                    text = "فعال بودن اعلانات",
                                    color = AmozeshgamTheme.colors["textColor"]!!
                                )
                            }

                        }
                    }
                }
                if (showWalletCharge.value) {
                    UiHandler.WalletChargeBottomSheet(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onDismissRequest = { showWalletCharge.value = false },
                        suggestionList = arrayOf(
                            100000,
                            200000,
                            300000,
                            400000,
                            500000,
                            600000,
                            700000,
                            800000,
                            900000,
                            1000000
                        )
                    ) {

                    }
                }
            }
        }
    }
}


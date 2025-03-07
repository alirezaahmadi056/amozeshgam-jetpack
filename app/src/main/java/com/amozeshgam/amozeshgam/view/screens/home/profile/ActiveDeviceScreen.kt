package com.amozeshgam.amozeshgam.view.screens.home.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetDevicesData
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.ActiveDevicesItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.profile.ActiveDevicesViewModel

@Composable
fun ViewActiveDevice(
    navController: NavController,
    viewModel: ActiveDevicesViewModel = hiltViewModel(),
) {
    val activeDevicesData = remember {
        mutableStateListOf<ApiResponseGetDevicesData>()
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    val currentIndexForDeActive = remember {
        mutableIntStateOf(-1)
    }
    UiHandler.ContentWithScaffold(
        title = "دستگاه های فعال",
        onBackButtonClick = {
            navController.popBackStack()
        }) {
        UiHandler.ContentWithLoading(
            loading = isLoading.value,
            worker = {
                viewModel.getActiveDevices().await().also {
                    activeDevicesData.addAll(it?.devices ?: listOf())
                }
                isLoading.value = false
                true
            },
            onDismissRequestForDefaultErrorHandler = {
                navController.popBackStack()
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .background(AmozeshgamTheme.colors["background"]!!)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End
            ) {
                item {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "دستگاه های فعال",
                        color = AmozeshgamTheme.colors["primary"]!!,
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        fontSize = 15.sp
                    )
                }
                items(activeDevicesData.size) { index ->
                    ActiveDevicesItem(
                        deviceName = activeDevicesData[index].deviceName,
                        deviceModel = activeDevicesData[index].deviceId,
                        deviceIP = ""
                    ) {
                        currentIndexForDeActive.intValue = index
                        viewModel.deActiveDevice(deviceId = activeDevicesData[index].deviceId)
                    }
                    if (index != activeDevicesData.size) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 15.dp),
                            color = AmozeshgamTheme.colors["borderColor"]!!
                        )
                    }
                }
                item {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = ".برای پایان دادن به فعالیت یک دستگاه، بر روی آن بزنید",
                        color = AmozeshgamTheme.colors["textColor"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                }
            }
            LaunchedEffect(Unit) {
                viewModel.deActiveDevice.observe(lifecycle) {
                    if (it) {
                        activeDevicesData.removeAt(currentIndexForDeActive.intValue)
                        Toast.makeText(context, "ما موفقیت حذف شد", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "خطا در حذف", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
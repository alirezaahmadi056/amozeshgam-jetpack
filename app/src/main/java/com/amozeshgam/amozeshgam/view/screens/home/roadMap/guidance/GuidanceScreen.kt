package com.amozeshgam.amozeshgam.view.screens.home.roadMap.guidance

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetFields
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.JobItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.roadMap.GuidanceViewModel

@Composable
fun ViewGuidance(navController: NavController, viewModel: GuidanceViewModel = hiltViewModel()) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val fieldsData = remember {
        mutableStateOf<ApiResponseGetFields?>(null)
    }
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = fieldsData.value != null,
        worker = {
            viewModel.getFieldData().await().also {
                fieldsData.value = it
            }
            isLoading.value = false
            true
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 10.dp), horizontalAlignment = Alignment.End
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(16f / 9f),
                    colors = CardDefaults.cardColors(containerColor = Color.Red),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {

                }
            }
            item {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "شغل مد نظرت رو انتخاب کن",
                    fontFamily = AmozeshgamTheme.fonts["bold"],
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
            }
            items(fieldsData.value!!.field.size) { index ->
                JobItem(
                    title = fieldsData.value!!.field[index].title,
                    imageUrl = fieldsData.value!!.field[index].image
                ) {
                    navController.navigate(
                        NavigationScreenHandler.GuidancePurchaseScreen.route + "/" + fieldsData.value!!.field[index].id
                    )
                }
            }
        }
    }
}
package com.amozeshgam.amozeshgam.view.screens.home.roadMap.job

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.view.items.JobItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.roadMap.JobViewModel

@Composable
fun ViewJobs(navController: NavController, viewModel: JobViewModel = hiltViewModel()) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 10.dp), horizontalAlignment = Alignment.End
    ) {
        item {
            Text(
                text = "شغل مد نظرت رو انتخاب کن",
                fontFamily = AmozeshgamTheme.fonts["bold"],
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
        }
        items(0) { index ->
            JobItem(
                title = "", imageUrl = ""
            ) {
                navController.navigate(
                    NavigationHandler.FieldScreen.route + "/" + 1
                )
            }
        }
    }
}
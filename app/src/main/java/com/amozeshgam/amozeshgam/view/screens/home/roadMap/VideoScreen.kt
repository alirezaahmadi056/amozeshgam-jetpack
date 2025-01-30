package com.amozeshgam.amozeshgam.view.screens.home.roadMap

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseVideo

@Composable
fun ViewVideo(videoItem: ArrayList<ApiResponseVideo>, onClick: (link: String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(videoItem.size) { index ->
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .aspectRatio(16f / 9f)
                    .clickable {
                        onClick(videoItem[index].video)
                    }
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = videoItem[index].video,
                    contentDescription = null
                )
            }
        }
    }
}
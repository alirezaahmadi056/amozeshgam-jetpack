package com.amozeshgam.amozeshgam.widget.view

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentHeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.view.ui.theme.primaryColor

class ViewRoadMapWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                Box(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .cornerRadius(20.dp)
                        .background(ImageProvider(R.drawable.bg_widget))
                ) {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_up),
                            contentDescription = null
                        )
                    }
                    LazyColumn(
                        modifier = GlanceModifier.padding(vertical = 8.dp).fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        items(200) {
                            Column(
                                modifier = GlanceModifier.padding(10.dp).fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = GlanceModifier.padding(10.dp).fillMaxWidth()
                                        .height(100.dp)
                                        .cornerRadius(10.dp).background(primaryColor)
                                ) {
                                    Row(
                                        modifier = GlanceModifier.padding(5.dp).fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            modifier = GlanceModifier.size(30.dp),
                                            provider = ImageProvider(resId = R.drawable.ic_arrow_left),
                                            contentDescription = null,
                                            contentScale = ContentScale.FillBounds,
                                            colorFilter = ColorFilter.tint(
                                                colorProvider = ColorProvider(Color.White)
                                            )
                                        )
                                        Text(
                                            modifier = GlanceModifier.defaultWeight()
                                                .padding(horizontal = 5.dp),
                                            text = "اسم مسیر", style = TextStyle(
                                                textAlign = TextAlign.Right,
                                                color = ColorProvider(color = Color.White)
                                            ),
                                            maxLines = 1
                                        )
                                        Image(
                                            modifier = GlanceModifier.size(40.dp),
                                            provider = ImageProvider(resId = R.drawable.ic_app_logo),
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(
                                                colorProvider = ColorProvider(
                                                    Color.White
                                                )
                                            )
                                        )
                                    }
                                }
                                Row(
                                    modifier = GlanceModifier.fillMaxWidth(),
                                ) {
                                    Text(text = "15%")
                                    Text(
                                        modifier = GlanceModifier.defaultWeight(),
                                        text = "درصد پیشرفت",
                                        style = TextStyle(
                                            textAlign = TextAlign.Right,
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
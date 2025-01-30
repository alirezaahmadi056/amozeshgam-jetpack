package com.amozeshgam.amozeshgam.widget.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.view.ui.theme.primaryColor
import com.amozeshgam.amozeshgam.widget.viewmodel.PodcastWidgetViewModel

class ViewPodcastWidget : GlanceAppWidget() {
    private val viewModel = @Composable
    fun(): PodcastWidgetViewModel {
        return hiltViewModel<PodcastWidgetViewModel>()
    }

    override suspend fun onDelete(context: Context, glanceId: GlanceId) {
        super.onDelete(context, glanceId)
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                Row(
                    modifier = GlanceModifier.fillMaxSize().cornerRadius(20.dp)
                                .padding(5.dp)
                        .background(ImageProvider(R.drawable.bg_widget)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = GlanceModifier.size(35.dp),
                        provider = ImageProvider(resId = R.drawable.ic_back_10sec),
                        contentDescription = null
                    )
                    Image(
                        modifier = GlanceModifier.size(35.dp),
                        provider = ImageProvider(resId = R.drawable.ic_play),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(colorProvider = ColorProvider(primaryColor))
                    )
                    Image(
                        modifier = GlanceModifier.size(35.dp),
                        provider = ImageProvider(resId = R.drawable.ic_next_10sec),
                        contentDescription = null
                    )
                    Text(
                        modifier = GlanceModifier.padding(10.dp).defaultWeight(),
                        text = "اسم پادکست",
                        style = TextStyle(
                            textAlign = TextAlign.Right,
                        ),
                        maxLines = 1
                    )
                    Image(
                        modifier = GlanceModifier.size(35.dp),
                        provider = ImageProvider(resId = R.drawable.ic_app_logo),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(colorProvider = ColorProvider(primaryColor))
                    )
                }
            }
        }
    }
}


package com.amozeshgam.amozeshgam.widget.receiver

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.amozeshgam.amozeshgam.widget.view.ViewRoadMapWidget

class ReceiverRoadMapWidget() :GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = ViewRoadMapWidget()
}
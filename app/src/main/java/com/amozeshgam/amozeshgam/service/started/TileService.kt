package com.amozeshgam.amozeshgam.service.started

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TileService : TileService() {
    override fun onTileAdded() {
        super.onTileAdded()
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()

        qsTile.updateTile()
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onClick() {
        super.onClick()
        qsTile.updateTile()
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://google.com")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        startActivityAndCollapse(
            PendingIntent.getActivity(
                this,
                200,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )


    }
}
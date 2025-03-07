package com.amozeshgam.amozeshgam.view.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.amozeshgam.amozeshgam.R

val LocalAmozeshgamColor = staticCompositionLocalOf {
    hashMapOf(
        "primary" to Color.Black
    )
}
val LocalAmozeshgamId = staticCompositionLocalOf {
    hashMapOf("bgLogin" to backgroundLogin)
}
val LocalAmozeshgamFont = staticCompositionLocalOf {
    hashMapOf("bold" to FontFamily(Font(R.font.bold)))
}
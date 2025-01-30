package com.amozeshgam.amozeshgam.view.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat
import com.amozeshgam.amozeshgam.handler.UiHandler

private val customThemeColorDark =
    hashMapOf(
        "primary" to primaryColorDark,
        "background" to backgroundColorDark,
        "focusBorderColor" to primaryColorDark,
        "textButtonColor" to primaryColorDark,
        "secondaryColor" to secondaryColorDark,
        "errorColor" to errorColor,
        "disableContainer" to disableContainer,
        "borderColor" to borderColor,
        "paymentBackgroundColor" to paymentBackgroundColorDark,
        "paymentTextColor" to paymentTextColorDark,
        "textColor" to textColorDark,
        "sliderActiveColor" to sliderActiveColorDark,
        "sliderInActiveColor" to sliderInActiveColorDark,
        "badgeBoxColor" to primaryColorDark,
        "itemColor" to itemColorDark,
        "blueColor" to primaryColor,
        "shadowColor" to shadowColor,
        "iconColor" to iconColor,
        "yellowColor" to primaryColorDark,
        "secondaryTextColor" to secondaryTextColor,
        "dialogBackground" to dialogBackground,
        "stepColor" to stepColor
    )


private val customThemeColorLight =
    hashMapOf(
        "primary" to primaryColor,
        "background" to backgroundColor,
        "focusBorderColor" to primaryColor,
        "textButtonColor" to primaryColor,
        "secondaryColor" to secondaryColor,
        "errorColor" to errorColor,
        "disableContainer" to disableContainer,
        "borderColor" to borderColor,
        "paymentBackgroundColor" to paymentBackgroundColor,
        "paymentTextColor" to paymentTextColor,
        "textColor" to textColor,
        "badgeBoxColor" to primaryColorDark,
        "sliderActiveColor" to sliderActiveColor,
        "sliderInActiveColor" to sliderInActiveColor,
        "itemColor" to itemColor,
        "blueColor" to primaryColor,
        "shadowColor" to primaryColor,
        "iconColor" to primaryColor,
        "yellowColor" to primaryColorDark,
        "secondaryTextColor" to secondaryTextColor,
        "dialogBackground" to backgroundColor,
        "stepColor" to stepColor
    )


private val customThemeIdLight =
    hashMapOf("bgLogin" to backgroundLogin, "amozeshgamBanner" to amozeshgamBanner)

private val customThemeIdDark =
    hashMapOf("bgLogin" to backgroundLoginDark, "amozeshgamBanner" to amozeshgamBannerDark)
private val customThemeFont =
    hashMapOf("bold" to fontBold, "regular" to fontRegular, "black" to fontBlack)

@Composable
fun AmozeshgamTheme(
    darkTheme: Boolean = UiHandler.themeState(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> customThemeColorDark
        else -> customThemeColorLight
    }
    val idAsset = when {
        darkTheme -> customThemeIdDark
        else -> customThemeIdLight
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme["primary"]!!.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Ltr,
        LocalAmozeshgamColor provides colorScheme,
        LocalAmozeshgamId provides idAsset,
        LocalAmozeshgamFont provides customThemeFont,
        content = content
    )
}

object AmozeshgamTheme {
    val colors @Composable get() = LocalAmozeshgamColor.current
    val assets @Composable get() = LocalAmozeshgamId.current
    val fonts @Composable get() = LocalAmozeshgamFont.current
}
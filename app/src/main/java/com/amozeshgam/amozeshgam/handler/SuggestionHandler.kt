package com.amozeshgam.amozeshgam.handler

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.platform.toClipEntry
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuggestionHandler @Inject constructor(@ApplicationContext private val context: Context) {

    private val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    fun getTextFromClipBoard(): String? {
        clipboardManager.primaryClip?.getItemAt(0)?.text?.let {
            return it.toString()
        }
        return null
    }

    fun setTextToTextProvider(value: String) {
        GlobalUiModel.textProviderData.value = value
    }
}

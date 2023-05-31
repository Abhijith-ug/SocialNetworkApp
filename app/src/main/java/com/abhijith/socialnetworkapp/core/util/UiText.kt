package com.abhijith.socialnetworkapp.core.util

import androidx.annotation.StringRes
import com.abhijith.socialnetworkapp.R

sealed class UiText{
    data class DynamicString(val value:String):UiText()
    data class StringResource(@StringRes val id :Int): UiText()
    companion object{
        fun unKnownError(): UiText{
            return UiText.StringResource(R.string.error_unknown)
        }
    }
}

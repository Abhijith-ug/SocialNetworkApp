package com.abhijith.socialnetworkapp.core.presentation.util

import com.abhijith.socialnetworkapp.core.util.Event
import com.abhijith.socialnetworkapp.core.util.UiText

sealed class UiEvent:Event() {
    data class ShowSnackBar(val uiText: UiText) : UiEvent()
    data class Navigate(val route:String): UiEvent()
    object NavigateUp : UiEvent()
}

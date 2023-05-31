package com.abhijith.socialnetworkapp.core.presentation.util

import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featureauth.presentation.login.LoginViewModel

sealed class UiEvent {
    data class SnackbarEvent(val uiText: UiText) : UiEvent()
    data class Navigate(val route:String): UiEvent()
}

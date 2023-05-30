package com.abhijith.socialnetworkapp.core.domain.states
import com.abhijith.socialnetworkapp.core.util.Error

data class StandardTextFieldState(
    val text:String = "",
    val error:Error? = null
)

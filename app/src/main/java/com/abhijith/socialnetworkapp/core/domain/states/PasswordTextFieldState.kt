package com.abhijith.socialnetworkapp.core.domain.states

data class PasswordTextFieldState(
    val text:String = "",
    val error:com.abhijith.socialnetworkapp.core.util.Error?= null,
    val isPasswordVisible:Boolean = false
)

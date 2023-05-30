package com.abhijith.socialnetworkapp.featureauth.util

sealed class AuthError: com.abhijith.socialnetworkapp.core.util.Error(){
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword: AuthError()
}


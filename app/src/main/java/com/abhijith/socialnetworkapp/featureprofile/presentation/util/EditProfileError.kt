package com.abhijith.socialnetworkapp.featureprofile.presentation.util

sealed class EditProfileError : com.abhijith.socialnetworkapp.core.util.Error(){
    object FieldEmpty : EditProfileError()
}

package com.abhijith.socialnetworkapp.featurepost.util

sealed class PostDescriptionError : com.abhijith.socialnetworkapp.core.util.Error(){
    object FieldEmpty: PostDescriptionError()
}

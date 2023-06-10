package com.abhijith.socialnetworkapp.featurepost.util

sealed class CommentError : com.abhijith.socialnetworkapp.core.util.Error(){
    object FieldEmpty:CommentError()
}

package com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail


sealed class PostDetailEvent{
    object LikePost: PostDetailEvent()
    object Comment:PostDetailEvent()
    data class LikeComment(val commentId:String):PostDetailEvent()
    data class EnteredComment(val comment:String):PostDetailEvent()
}

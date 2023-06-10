package com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail


sealed class PostDetailEvent{
    object LikePost: PostDetailEvent()
    data class Comment(val comment:String):PostDetailEvent()
    data class LikeComment(val commentId:String):PostDetailEvent()
    object SharePost:PostDetailEvent()
}

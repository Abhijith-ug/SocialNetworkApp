package com.abhijith.socialnetworkapp.domain.util


sealed class ActivityAction{
    object LikedPost:ActivityAction()
    object CommentedOnPost:ActivityAction()
}

package com.abhijith.socialnetworkapp.featurepost.presentation.mainFeed

sealed class MainFeedEvents{
    data class LikedPost(val postId:String): MainFeedEvents()
}

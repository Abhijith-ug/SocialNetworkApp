package com.abhijith.socialnetworkapp.featurepost.presentation.mainFeed

sealed class MainFeedEvents{
    object LoadMorePosts : MainFeedEvents()
    object LoadedPage : MainFeedEvents()
}

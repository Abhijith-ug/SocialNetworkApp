package com.abhijith.socialnetworkapp.featurepost.presentation.mainFeed

import com.abhijith.socialnetworkapp.core.domain.models.Post

data class MainFeedState(
    val isLoadingFirstTime: Boolean = true,
    val isLoadingNewPosts: Boolean = false,
)

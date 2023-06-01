package com.abhijith.socialnetworkapp.featurepost.domain.use_case

data class PostUseCases(
    val getPostsForFollowUseCase: GetPostForFollowsUseCase,
    val createPostUseCase: CreatePostUseCase
)

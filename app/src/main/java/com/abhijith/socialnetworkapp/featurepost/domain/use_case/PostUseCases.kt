package com.abhijith.socialnetworkapp.featurepost.domain.use_case

data class PostUseCases(
    val getPostsForFollowUseCase: GetPostForFollowsUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getPostDetailsUseCase:GetPostDetailsUseCase,
    val getCommentsForPostUseCase: GetCommentsForPostUseCase,
    val createCommentUseCase: CreateCommentUseCase,
    val toggleLikeForParentUseCase: ToggleLikeForParentUseCase
)

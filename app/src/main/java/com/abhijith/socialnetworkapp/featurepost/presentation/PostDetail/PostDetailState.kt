package com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail

import com.abhijith.socialnetworkapp.core.domain.models.Comment
import com.abhijith.socialnetworkapp.core.domain.models.Post

data class PostDetailState(
    val post:Post?=null,
    val isLoading:Boolean = false,
    val comments:List<Comment> = emptyList(),
    val isLoadingComments:Boolean = false
)
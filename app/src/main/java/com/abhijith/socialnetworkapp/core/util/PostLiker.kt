package com.abhijith.socialnetworkapp.core.util

import com.abhijith.socialnetworkapp.core.domain.models.Post

interface PostLiker {
    suspend fun toggleLike(
        posts: List<Post>,
        parentId: String,
        onRequest: suspend (isLiked:Boolean) -> SimpleResource,
        onStateUpdated: (List<Post>) -> Unit
    )
}
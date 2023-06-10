package com.abhijith.socialnetworkapp.featurepost.domain.use_case

import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository

class GetPostDetailsUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId:String):Resource<Post>{
        return repository.getPostDetails(postId)
    }
}
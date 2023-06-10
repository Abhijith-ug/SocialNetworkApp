package com.abhijith.socialnetworkapp.featurepost.domain.use_case

import com.abhijith.socialnetworkapp.core.domain.models.Comment
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository

class GetCommentsForPostUseCase(
    private val repository:PostRepository
) {

    suspend operator fun invoke(postId:String):Resource<List<Comment>>{
        return repository.getCommentsForPost(postId)
    }
}
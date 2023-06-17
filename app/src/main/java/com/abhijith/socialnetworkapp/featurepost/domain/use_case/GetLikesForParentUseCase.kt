package com.abhijith.socialnetworkapp.featurepost.domain.use_case

import com.abhijith.socialnetworkapp.core.domain.models.UserItem
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository

class GetLikesForParentUseCase(
    private val repository:PostRepository
) {
    suspend operator fun invoke(parentId:String):Resource<List<UserItem>>{
        return repository.getLikesForParent(parentId)
    }
}
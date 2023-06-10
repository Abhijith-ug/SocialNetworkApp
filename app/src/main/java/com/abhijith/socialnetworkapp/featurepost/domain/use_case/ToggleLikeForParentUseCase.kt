package com.abhijith.socialnetworkapp.featurepost.domain.use_case

import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository

class ToggleLikeForParentUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(parentId:String,parentType:Int,isLiked:Boolean):SimpleResource{

        return if (isLiked){
             repository.unLikeParent(parentId,parentType)
        }else{
             repository.likeParent(parentId,parentType)
        }

    }
}
package com.abhijith.socialnetworkapp.featurepost.domain.use_case

import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository

class CreateCommentUseCase(
    private val repository: PostRepository
) {
   suspend operator fun invoke(postId:String,comment:String):SimpleResource{
        if (comment.isBlank()){
            return Resource.Error(
             UiText.StringResource(R.string.this_field_cant_be_empty)
            )
        }
       if (postId.isBlank()){
           return Resource.Error(
               UiText.unKnownError()
           )
       }
        return repository.createComment(postId,comment)
    }
}
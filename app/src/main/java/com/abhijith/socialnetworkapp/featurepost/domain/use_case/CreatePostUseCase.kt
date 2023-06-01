package com.abhijith.socialnetworkapp.featurepost.domain.use_case

import android.net.Uri
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository

class CreatePostUseCase(
    private val repository:PostRepository
) {

    suspend operator fun invoke(
        description:String,
        imageUri:Uri
    ):SimpleResource {
      return  repository.createPost(description,imageUri)
    }
}
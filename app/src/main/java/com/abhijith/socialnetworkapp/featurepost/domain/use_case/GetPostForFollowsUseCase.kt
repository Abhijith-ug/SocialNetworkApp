package com.abhijith.socialnetworkapp.featurepost.domain.use_case

import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostForFollowsUseCase(
    private val repository: PostRepository
) {

     operator fun invoke(

    ): Flow<PagingData<Post>> {
          return repository.posts
    }
}
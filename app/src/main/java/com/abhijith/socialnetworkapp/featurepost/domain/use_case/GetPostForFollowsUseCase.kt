package com.abhijith.socialnetworkapp.featurepost.domain.use_case

import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostForFollowsUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
         page:Int,
         pageSize:Int = Constants.DEFAULT_PAGE_SIZE
    ): Resource<List<Post>> {
          return repository.getPostsForFollows(page,pageSize)
    }
}
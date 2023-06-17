package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.domain.repository.ProfileRepository
import com.abhijith.socialnetworkapp.core.util.Resource

class GetPostsForProfileUseCase(
    private val repository: ProfileRepository
) {

   suspend operator fun invoke(userId:String,page:Int): Resource<List<Post>>{
        return repository.getPostPaged(
            userId = userId,
            page = page
        )
    }
}
package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.featureprofile.domain.repository.ProfileRepository
import java.util.concurrent.Flow

class GetPostsForProfileUseCase(
    private val repository: ProfileRepository
) {

    operator fun invoke(userId:String):kotlinx.coroutines.flow.Flow<PagingData<Post>>{
        return repository.getPostPaged(userId)
    }
}
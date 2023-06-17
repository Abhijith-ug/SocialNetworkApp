package com.abhijith.socialnetworkapp.core.domain.usecase

import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.core.domain.repository.ProfileRepository

class ToggleFollowUseCaseForUserUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        userId:String,isFollowing:Boolean
    ):SimpleResource{
      return  if (isFollowing){
            repository.unfollowUser(userId)
        }else{
            repository.followUser(userId)
        }
    }
}
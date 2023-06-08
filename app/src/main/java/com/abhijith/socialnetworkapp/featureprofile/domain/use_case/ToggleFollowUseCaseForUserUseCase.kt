package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.featureprofile.domain.repository.ProfileRepository

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
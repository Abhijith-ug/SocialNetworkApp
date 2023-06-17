package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Profile
import com.abhijith.socialnetworkapp.core.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId:String):Resource<Profile>{
        return repository.getProfile(userId)
    }
}
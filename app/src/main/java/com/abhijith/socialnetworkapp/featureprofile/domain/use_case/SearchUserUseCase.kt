package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import com.abhijith.socialnetworkapp.core.domain.models.UserItem
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.featureprofile.domain.repository.ProfileRepository

class SearchUserUseCase(
    private val repository:ProfileRepository
) {
    suspend operator fun invoke(query:String):Resource<List<UserItem>>{
        if (query.isBlank()){
            return Resource.Success(data = emptyList())
        }
        return repository.searchUser(query)
    }
}
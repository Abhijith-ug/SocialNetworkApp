package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import com.abhijith.socialnetworkapp.core.domain.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository
) {
    operator fun invoke(){
        repository.logout()
    }
}
package com.abhijith.socialnetworkapp.featureauth.domain.use_case

import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.featureauth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): SimpleResource{
        return repository.authenticate()
    }
}
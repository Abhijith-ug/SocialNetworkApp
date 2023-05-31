package com.abhijith.socialnetworkapp.featureauth.domain.use_case

import com.abhijith.socialnetworkapp.core.util.simpleResource
import com.abhijith.socialnetworkapp.featureauth.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(
        email:String,
        username:String,
        password:String
    ):simpleResource{
          return repository.register(email.trim(),username.trim(),password.trim())
    }
}
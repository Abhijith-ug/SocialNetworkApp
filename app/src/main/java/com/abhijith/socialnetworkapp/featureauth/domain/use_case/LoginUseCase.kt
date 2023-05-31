package com.abhijith.socialnetworkapp.featureauth.domain.use_case

import com.abhijith.socialnetworkapp.featureauth.domain.models.LoginResult
import com.abhijith.socialnetworkapp.featureauth.domain.repository.AuthRepository
import com.abhijith.socialnetworkapp.featureauth.util.AuthError

class LoginUseCase(
    private val repository: AuthRepository
){
    suspend operator fun  invoke(email:String,password:String):LoginResult{
         val emailError = if (email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if (password.isBlank()) AuthError.FieldEmpty else null

        if (emailError!=null || passwordError!=null){
            return LoginResult(emailError,passwordError)
        }

        return LoginResult(
            result = repository.login(email,password)
        )
    }

}
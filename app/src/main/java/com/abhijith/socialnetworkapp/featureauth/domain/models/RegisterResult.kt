package com.abhijith.socialnetworkapp.featureauth.domain.models

import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.featureauth.util.AuthError

data class RegisterResult(
    val emailError: AuthError? = null,
    val usernameError:AuthError? = null,
    val passwordError:AuthError? = null,
    val result: SimpleResource? = null
)

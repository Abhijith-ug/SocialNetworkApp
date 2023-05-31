package com.abhijith.socialnetworkapp.featureauth.domain.repository

import com.abhijith.socialnetworkapp.core.data.dto.response.BasicApiResponse
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.simpleResource
import com.abhijith.socialnetworkapp.featureauth.data.dto.request.CreateAccountRequest

interface AuthRepository {

    suspend fun register(email:String,username:String,password:String): simpleResource
}
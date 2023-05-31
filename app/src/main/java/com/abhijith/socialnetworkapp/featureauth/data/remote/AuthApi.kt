package com.abhijith.socialnetworkapp.featureauth.data.remote

import com.abhijith.socialnetworkapp.core.data.dto.response.BasicApiResponse
import com.abhijith.socialnetworkapp.featureauth.data.dto.request.CreateAccountRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(@Body request: CreateAccountRequest): BasicApiResponse

    companion object{
        const val BASE_URL  = "http://192.168.64.153:8001/"
    }
}
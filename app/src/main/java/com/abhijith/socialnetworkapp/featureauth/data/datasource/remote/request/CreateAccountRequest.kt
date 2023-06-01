package com.abhijith.socialnetworkapp.featureauth.data.datasource.remote.request

data class CreateAccountRequest(
    val email:String,
    val username:String,
    val password:String
)

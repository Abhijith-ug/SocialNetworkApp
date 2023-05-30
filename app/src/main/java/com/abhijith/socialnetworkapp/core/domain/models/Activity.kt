package com.abhijith.socialnetworkapp.core.domain.models

import com.abhijith.socialnetworkapp.domain.util.ActivityAction

data class Activity(
    val username:String,
    val actionType:ActivityAction,
    val formattedTime:String
)

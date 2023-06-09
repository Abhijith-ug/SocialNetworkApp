package com.abhijith.socialnetworkapp.core.domain.models

import com.abhijith.socialnetworkapp.domain.util.ActivityType

data class Activity(
    val userId:String,
    val parentId:String,
    val username:String,
    val activityType:ActivityType,
    val formattedTime:String
)

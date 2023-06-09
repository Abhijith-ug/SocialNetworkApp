package com.abhijith.socialnetworkapp.featureactivity.data.remote.dto

import com.abhijith.socialnetworkapp.core.domain.models.Activity
import com.abhijith.socialnetworkapp.domain.util.ActivityType
import java.text.SimpleDateFormat
import java.util.*

data class ActivityDto(
    val timeStamp:Long,
    val userId:String,
    val parentId:String,
    val type:Int,
    val username:String,
    val id : String
){
    fun toActivity():Activity{
        return Activity(
            userId = userId,
            parentId = parentId,
            username = username,
            activityType = when(type){
                ActivityType.FollowedUser.type -> ActivityType.FollowedUser
                ActivityType.LikedPost.type -> ActivityType.LikedPost
                ActivityType.LikedComment.type -> ActivityType.LikedComment
                ActivityType.CommentedOnPost.type -> ActivityType.CommentedOnPost
                else -> ActivityType.FollowedUser
            },
            formattedTime = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).run {
                format(timeStamp)
            }
        )
    }
}

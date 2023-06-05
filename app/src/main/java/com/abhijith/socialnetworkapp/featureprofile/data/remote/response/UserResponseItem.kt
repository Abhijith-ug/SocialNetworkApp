package com.abhijith.socialnetworkapp.featureprofile.data.remote.response

import com.abhijith.socialnetworkapp.featureprofile.domain.model.UserItem

data class UserResponseItem(
    val userId:String,
    val username:String,
    val profilePictureUrl:String,
    val bio:String,
    val isFollowing:Boolean
){
    fun toUserItem():UserItem{
        return UserItem(
            userId = userId,
            username = username,
            profilePictureUrl = profilePictureUrl,
            bio = bio,
            isFollowing = isFollowing
        )
    }
}

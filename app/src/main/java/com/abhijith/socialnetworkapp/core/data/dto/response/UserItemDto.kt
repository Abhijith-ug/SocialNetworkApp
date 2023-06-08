package com.abhijith.socialnetworkapp.core.data.dto.response

import com.abhijith.socialnetworkapp.core.domain.models.UserItem

data class UserItemDto(
    val userId:String,
    val username:String,
    val profilePictureUrl:String,
    val bio:String,
    val isFollowing:Boolean
){

    fun toUserItem(): UserItem {
        return UserItem(
            userId = userId,
            username = username,
            profilePictureUrl = profilePictureUrl,
            bio = bio,
            isFollowing = isFollowing
        )
    }
}


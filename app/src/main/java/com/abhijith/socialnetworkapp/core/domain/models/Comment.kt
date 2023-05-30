package com.abhijith.socialnetworkapp.core.domain.models

import java.util.concurrent.CountDownLatch

data class Comment(
    val commentId:Int = -1,
    val username:String = "",
    val profilePictureUrl:String ="",
    val timeStamp:Long = System.currentTimeMillis(),
    val comment:String = "",
    val isLiked:Boolean = false,
    val likeCount:Int = 12
)
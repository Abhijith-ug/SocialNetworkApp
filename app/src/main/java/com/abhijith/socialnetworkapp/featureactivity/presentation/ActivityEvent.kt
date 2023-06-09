package com.abhijith.socialnetworkapp.featureactivity.presentation

sealed class ActivityEvent{
    data class ClickedOnUser(val userId:String):ActivityEvent()
    data class ClickedOnParent(val parentId:String):ActivityEvent()
}

package com.abhijith.socialnetworkapp.featureprofile.presentation.profile

sealed class ProfileEvent{
    data class LikePost(val postId:String) : ProfileEvent()
    object DismissLogoutDialog: ProfileEvent()
    object ShowLogoutDialog: ProfileEvent()
    object Logout:ProfileEvent()
}

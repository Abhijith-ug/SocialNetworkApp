package com.abhijith.socialnetworkapp.featurepost.presentation.createpost

import android.net.Uri

sealed class CreatePostEvent{
    data class EnterDescription(val value:String):CreatePostEvent()
    data class PickImage(val uri:Uri?):CreatePostEvent()
    data class CropImage(val uri:Uri?):CreatePostEvent()
    object PostImage : CreatePostEvent()
}

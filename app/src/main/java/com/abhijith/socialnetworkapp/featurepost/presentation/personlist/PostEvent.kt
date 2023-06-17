package com.abhijith.socialnetworkapp.featurepost.presentation.personlist

import com.abhijith.socialnetworkapp.core.util.Event

sealed class PostEvent:Event(){
    object onLiked:PostEvent()
}

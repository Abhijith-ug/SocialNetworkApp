package com.abhijith.socialnetworkapp.featurepost.presentation.personlist

sealed class PersonListEvent {
    data class ToggleFollowStateForUser(val userId:String):PersonListEvent()
}

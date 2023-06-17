package com.abhijith.socialnetworkapp.featurepost.presentation.personlist

import com.abhijith.socialnetworkapp.core.domain.models.UserItem

data class PersonListState(
    val users:List<UserItem> = emptyList(),
    val isLoading:Boolean = false
)

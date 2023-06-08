package com.abhijith.socialnetworkapp.featureprofile.presentation.search

import com.abhijith.socialnetworkapp.core.domain.models.UserItem

data class SearchState (
    val userItems:List<UserItem> = emptyList(),
    val isLoading:Boolean = false
        )
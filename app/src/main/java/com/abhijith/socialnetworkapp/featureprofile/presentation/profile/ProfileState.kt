package com.abhijith.socialnetworkapp.featureprofile.presentation.profile

import com.abhijith.socialnetworkapp.featureprofile.domain.model.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val isLogoutDialogVisible:Boolean = false
)
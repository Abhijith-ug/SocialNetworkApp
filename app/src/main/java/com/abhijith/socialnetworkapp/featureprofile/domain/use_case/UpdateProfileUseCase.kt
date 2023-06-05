package com.abhijith.socialnetworkapp.featureprofile.domain.use_case

import android.net.Uri
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featureprofile.domain.model.UpdateProfileData
import com.abhijith.socialnetworkapp.featureprofile.domain.repository.ProfileRepository
import com.abhijith.socialnetworkapp.featureprofile.domain.util.ProfileConstants

class UpdateProfileUseCase(
    private val repository:ProfileRepository
) {

    suspend operator fun invoke(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerUri:Uri?
    ):SimpleResource{

        if (updateProfileData.username.isBlank()){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_username_empty)
            )
        }
        val isValidGitHubUrl = ProfileConstants.GITHUB_PROFILE_REGEX.matches(updateProfileData.gitHubUrl)
        if (!isValidGitHubUrl){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_invalid_github_url)
            )
        }
        val isValidInstagramUrl = ProfileConstants.INSTAGRAM_PROFILE_REGEX.matches(updateProfileData.instagramUrl)
        if (!isValidInstagramUrl){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_invalid_instagram_url)
            )
        }
        val isValidLinkedInUrl = ProfileConstants.LINKED_IN_PROFILE_REGEX.matches(updateProfileData.linkedInUrl)
        if (!isValidInstagramUrl){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_invalid_linkedIn_url)
            )
        }
        return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerUri
        )
    }
}
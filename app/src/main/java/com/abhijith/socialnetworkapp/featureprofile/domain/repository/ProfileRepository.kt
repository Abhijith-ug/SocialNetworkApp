package com.abhijith.socialnetworkapp.featureprofile.domain.repository

import android.net.Uri
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Profile
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Skill
import com.abhijith.socialnetworkapp.featureprofile.domain.model.UpdateProfileData

interface ProfileRepository {

    suspend fun getProfile(userId:String): Resource<Profile>

    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri:Uri?
    ):SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>


}
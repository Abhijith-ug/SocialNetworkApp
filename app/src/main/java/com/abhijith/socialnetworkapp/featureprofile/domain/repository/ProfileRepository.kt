package com.abhijith.socialnetworkapp.featureprofile.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Profile
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Skill
import com.abhijith.socialnetworkapp.featureprofile.domain.model.UpdateProfileData
import java.util.concurrent.Flow

interface ProfileRepository {


     fun getPostPaged(userId:String): kotlinx.coroutines.flow.Flow<PagingData<Post>>
    suspend fun getProfile(userId:String): Resource<Profile>



    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri:Uri?
    ):SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>


}
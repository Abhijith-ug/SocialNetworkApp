package com.abhijith.socialnetworkapp.featureprofile.data.repository

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.data.remote.PostApi
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Constants.BASE_URL
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featurepost.data.datasource.pagesource.PostSource
import com.abhijith.socialnetworkapp.featureprofile.data.remote.ProfileApi
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Profile
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Skill
import com.abhijith.socialnetworkapp.featureprofile.domain.model.UpdateProfileData
import com.abhijith.socialnetworkapp.featureprofile.domain.repository.ProfileRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi,
    private val postApi:PostApi,
    private val gson:Gson
) : ProfileRepository {



    override suspend fun getProfile(userId: String): Resource<Profile> {
        return try {
            val response = profileApi.getProfile(userId)
            Log.d("abii", "${response.data} ")
            if (response.successful) {
                val profile = response.data?.toProfile()?.copy(
                    bannerUrl = "${BASE_URL}${response.data.toProfile().bannerUrl}",
                    profilePictureUrl = "${BASE_URL}${response.data.toProfile().profilePictureUrl}"
                )
                Resource.Success(profile)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(id = R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }
    }

    override suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource {
             val bannerFile = bannerImageUri?.toFile()
             val profilePictureFile = profilePictureUri?.toFile()
        return try {
            val response = profileApi.updateProfile(
                bannerImage = bannerFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "banner_image",
                            bannerFile.name,
                            bannerFile.asRequestBody()
                        )
                },
                profilePicture = profilePictureFile?.let {
                    MultipartBody.Part
                        .createFormData(
                            "profile_picture",
                            profilePictureFile.name,
                            profilePictureFile.asRequestBody()
                        )
                },
                updateProfileData = MultipartBody.Part
                    .createFormData(
                        "update_profile_data",
                        gson.toJson(
                            updateProfileData
                        )

                    )

            )
            if(response.successful){
                Resource.Success(Unit)
            }else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(id = R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }



    }


    override suspend fun getSkills(): Resource< List<Skill>> {
        return try {
            val response = profileApi.getSkills()
            Resource.Success(
                    data = response.map {
                        it.toSkill()
                    }
                )
            }
         catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }
    }

    override fun getPostPaged(userId: String): Flow<PagingData<Post>> {
        return  Pager(PagingConfig(pageSize = Constants.PAGE_SIZE_POSTS)){
            PostSource(postApi,PostSource.Source.Profile(userId))
        }.flow
    }


}
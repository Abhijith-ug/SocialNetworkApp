package com.abhijith.socialnetworkapp.featurepost.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.domain.util.getFileName
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featureauth.data.datasource.remote.request.CreateAccountRequest
import com.abhijith.socialnetworkapp.featurepost.data.datasource.pagesource.PostSource
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.PostApi
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.request.CreatePostRequest
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PostRepositoryImp(
    private val api:PostApi,
    private val gson: Gson,
):PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE_POSTS)){
            PostSource(api)
        }.flow

    override suspend fun createPost(description: String,imageUri: Uri): SimpleResource {
        val request = CreatePostRequest(description)
        val file = imageUri.toFile()

        return try {
            val response = api.createPost(
                 postData = MultipartBody.Part
                     .createFormData(
                         "post_data",
                         gson.toJson(request)
                     ),
                postImage = MultipartBody.Part
                    .createFormData(
                        name = "post_image",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
            )
            if (response.successful) {
                Resource.Success(Unit)
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
}
package com.abhijith.socialnetworkapp.featurepost.data.repository

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featurepost.data.datasource.pagesource.PostSource
import com.abhijith.socialnetworkapp.core.data.remote.PostApi
import com.abhijith.socialnetworkapp.core.domain.models.Comment
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.request.CreateCommentRequest
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.request.CreatePostRequest
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.request.LikeUpdateRequest
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository
import com.abhijith.socialnetworkapp.featureprofile.data.remote.request.FollowUpdateRequest
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImp(
    private val api: PostApi,
    private val gson: Gson,
) : PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)) {
            PostSource(api, PostSource.Source.Follows)
        }.flow

    override suspend fun createPost(description: String, imageUri: Uri): SimpleResource {
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

    override suspend fun getPostDetails(postId: String): Resource<Post> {
        return try {
            Log.d("POSTDETAIL", "Before")
            val response = api.getPostDetails(postId = postId)
            Log.d("POSTDETAIL", "${response}")
            if (response.successful) {
                Resource.Success(response.data)
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

    override suspend fun getCommentsForPost(postId: String): Resource<List<Comment>> {
        return try {
            val comments = api.getCommentsForPost(postId).map {
                it.toComment()
            }
            Resource.Success(comments)
        }catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }
    }

    override suspend fun createComment(postId: String, comment: String): SimpleResource {
        return try {
            val response = api.createComment(
                    CreateCommentRequest(
                        comment = comment,
                        postId = postId
                    )
            )
            if (response.successful) {
                Resource.Success(response.data)
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

    override suspend fun likeParent(parentId: String, parentType: Int): SimpleResource {
        return try {
            val response = api.likeParent(
                LikeUpdateRequest(
                     parentId = parentId,
                    parentType = parentType
                )
            )
            if (response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(id = R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }    }

    override suspend fun unLikeParent(parentId: String, parentType: Int): SimpleResource {
        return try {
            val response = api.unlikeParent(
                parentId = parentId,
                parentType = parentType
            )
            if (response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(id = R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }     }
}
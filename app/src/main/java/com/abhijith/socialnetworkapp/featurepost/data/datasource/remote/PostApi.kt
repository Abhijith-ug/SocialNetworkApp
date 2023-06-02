package com.abhijith.socialnetworkapp.featurepost.data.datasource.remote

import com.abhijith.socialnetworkapp.core.data.dto.response.BasicApiResponse
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.request.CreatePostRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface PostApi {

    @GET("/api/post/get")
    suspend fun getPostForFollows(
        @Query("page") page:Int,
       @Query("pageSize") pageSize:Int
    ): List<Post>

    @Multipart
    @POST("/api/post/create")
    suspend fun createPost(
        @Part postData: MultipartBody.Part,
        @Part postImage:MultipartBody.Part
    ):BasicApiResponse<Unit>

    companion object{
        const val BASE_URL = "http://192.168.64.145:8001/"
    }
}
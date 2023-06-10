package com.abhijith.socialnetworkapp.core.data.remote

import com.abhijith.socialnetworkapp.core.data.dto.response.BasicApiResponse
import com.abhijith.socialnetworkapp.core.domain.models.Comment
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.dto.CommentDto
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.request.CreateCommentRequest
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.request.CreatePostRequest
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.request.LikeUpdateRequest
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

    @GET("/api/user/posts")
    suspend fun getPostsForProfile(
        @Query("userId") userId:String,
        @Query("page") page:Int,
        @Query("pageSize") pageSize:Int
    ):List<Post>

    @GET("/api/post/details")
    suspend fun getPostDetails(
        @Query("postId") postId:String
    ):BasicApiResponse<Post>

    @GET("/api/comment/get")
    suspend fun getCommentsForPost(
        @Query("postId") postId:String
    ):List<CommentDto>

    @POST("/api/comment/create")
    suspend fun createComment(
         @Body request:CreateCommentRequest
    ): BasicApiResponse<Unit>

    @POST("/api/like")
    suspend fun likeParent(
        @Body request: LikeUpdateRequest
    ): BasicApiResponse<Unit>

    @DELETE("/api/unlike")
    suspend fun unlikeParent(
        @Query("parentId") parentId:String,
        @Query("parentType") parentType:Int
    ):BasicApiResponse<Unit>
}
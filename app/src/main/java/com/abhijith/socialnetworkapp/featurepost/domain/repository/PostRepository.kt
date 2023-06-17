package com.abhijith.socialnetworkapp.featurepost.domain.repository

import android.net.Uri
import androidx.compose.foundation.pager.PageSize
import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Comment
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.domain.models.UserItem
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

   suspend fun getPostsForFollows(page:Int,pageSize:Int):Resource<List<Post>>
   suspend fun createPost(description:String,imageUri:Uri): SimpleResource


   suspend fun getPostDetails(postId:String):Resource<Post>

   suspend fun getCommentsForPost(postId: String):Resource<List<Comment>>

   suspend fun createComment(postId: String,comment: String):SimpleResource

   suspend fun likeParent(parentId:String,parentType:Int):SimpleResource

    suspend fun unLikeParent(parentId:String,parentType:Int):SimpleResource

    suspend fun getLikesForParent(parentId:String): Resource<List<UserItem>>


}
package com.abhijith.socialnetworkapp.featurepost.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

  val posts: Flow<PagingData<Post>>

   suspend fun createPost(description:String,imageUri:Uri): SimpleResource

}
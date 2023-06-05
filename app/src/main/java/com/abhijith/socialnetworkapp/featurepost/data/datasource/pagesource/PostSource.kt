package com.abhijith.socialnetworkapp.featurepost.data.datasource.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.data.remote.PostApi
import retrofit2.HttpException
import java.io.IOException

class PostSource(private val api: PostApi
,private val source: Source) : PagingSource<Int, Post>() {

    private var currentPage = 0


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPage = params.key ?: 0
            val posts = when(source){
                is Source.Follows -> {
                    api.getPostForFollows(
                        page = nextPage,
                        pageSize = Constants.PAGE_SIZE_POSTS
                    )
                }
                is Source.Profile -> api.getPostsForProfile(
                    userId = source.userId,
                    page = nextPage,
                    pageSize = Constants.PAGE_SIZE_POSTS
                )

            }

            LoadResult.Page(
                data = posts,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (posts.isEmpty()) null else currentPage + 1
            ).also {
                currentPage++
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    sealed class Source{
        object Follows:Source()
        data class Profile(val userId:String):Source()
    }
}
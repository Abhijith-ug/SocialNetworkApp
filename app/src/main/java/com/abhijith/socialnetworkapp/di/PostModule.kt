package com.abhijith.socialnetworkapp.di

import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.data.remote.PostApi
import com.abhijith.socialnetworkapp.featurepost.data.repository.PostRepositoryImp
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostApi(client:OkHttpClient): PostApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
            .create(PostApi::class.java)
    }
    @Provides
    @Singleton
    fun providePostRepository(api: PostApi, gson: Gson):PostRepository{
        return PostRepositoryImp(api,gson)
    }

    @Provides
    @Singleton
    fun providePostUseCases(repository: PostRepository):PostUseCases{
        return PostUseCases(
            getPostsForFollowUseCase = GetPostForFollowsUseCase(repository),
            createPostUseCase = CreatePostUseCase(repository),
            getPostDetailsUseCase = GetPostDetailsUseCase(repository),
            getCommentsForPostUseCase = GetCommentsForPostUseCase(repository),
            createCommentUseCase = CreateCommentUseCase(repository),
            toggleLikeForParentUseCase = ToggleLikeForParentUseCase(repository)
        )
    }
}
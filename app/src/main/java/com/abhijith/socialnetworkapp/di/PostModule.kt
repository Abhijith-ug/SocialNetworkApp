package com.abhijith.socialnetworkapp.di

import android.content.Context
import com.abhijith.socialnetworkapp.featurepost.data.datasource.remote.PostApi
import com.abhijith.socialnetworkapp.featurepost.data.repository.PostRepositoryImp
import com.abhijith.socialnetworkapp.featurepost.domain.repository.PostRepository
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.CreatePostUseCase
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.GetPostForFollowsUseCase
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.PostUseCases
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providePostApi(client:OkHttpClient):PostApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostApi.BASE_URL)
            .client(client)
            .build()
            .create(PostApi::class.java)
    }
    @Provides
    @Singleton
    fun providePostRepository(api:PostApi,gson: Gson,@ApplicationContext appContext: Context):PostRepository{
        return PostRepositoryImp(api,gson,appContext)
    }

    @Provides
    @Singleton
    fun providePostUseCases(repository: PostRepository):PostUseCases{
        return PostUseCases(
            getPostsForFollowUseCase = GetPostForFollowsUseCase(repository),
            createPostUseCase = CreatePostUseCase(repository)
        )
    }
}
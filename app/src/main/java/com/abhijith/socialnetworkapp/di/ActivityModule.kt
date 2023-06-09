package com.abhijith.socialnetworkapp.di

import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.featureactivity.data.remote.ActivityApi
import com.abhijith.socialnetworkapp.featureactivity.data.repository.ActivityRepositoryImpl
import com.abhijith.socialnetworkapp.featureactivity.domain.repository.ActivityRepository
import com.abhijith.socialnetworkapp.featureactivity.domain.use_case.GetActivityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActivityModule {


    @Provides
    @Singleton
    fun provideActivityApi(client:OkHttpClient):ActivityApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActivityApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetActivityRepository(api:ActivityApi):ActivityRepository{
        return ActivityRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetActivitiesUseCase(repository: ActivityRepository):GetActivityUseCase{
        return GetActivityUseCase(repository)
    }
}
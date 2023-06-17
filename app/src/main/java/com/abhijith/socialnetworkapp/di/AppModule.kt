package com.abhijith.socialnetworkapp.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.abhijith.socialnetworkapp.core.domain.repository.ProfileRepository
import com.abhijith.socialnetworkapp.core.domain.usecase.GetOwnUserIdUseCase
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.DefaultPostLiker
import com.abhijith.socialnetworkapp.core.util.PostLiker
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences{
       return app.getSharedPreferences(
           Constants.SHARED_PREF_NAME,
           MODE_PRIVATE
       )
    }



    @Provides
    @Singleton
    fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor {
                val token = sharedPreferences.getString(Constants.KEY_JWT_TOKEN,"")
            val modifiedRequest =    it.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                .build()
                it.proceed(modifiedRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun providePostLiker():PostLiker{
        return DefaultPostLiker()
    }

    @Provides
    @Singleton
    fun provideGson():Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideGetOwnUserIdUseCase(sharedPreferences: SharedPreferences):GetOwnUserIdUseCase{
        return GetOwnUserIdUseCase(sharedPreferences)
    }




}
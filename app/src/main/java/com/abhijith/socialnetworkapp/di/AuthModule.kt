package com.abhijith.socialnetworkapp.di

import android.content.SharedPreferences
import android.util.Log
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.featureauth.data.datasource.remote.AuthApi
import com.abhijith.socialnetworkapp.featureauth.data.repository.AuthRepositoryImpl
import com.abhijith.socialnetworkapp.featureauth.domain.repository.AuthRepository
import com.abhijith.socialnetworkapp.featureauth.domain.use_case.AuthenticateUseCase
import com.abhijith.socialnetworkapp.featureauth.domain.use_case.LoginUseCase
import com.abhijith.socialnetworkapp.featureauth.domain.use_case.RegisterUseCase
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
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(client:OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }




    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, sharedPreferences: SharedPreferences):AuthRepository{
        return AuthRepositoryImpl(api, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository:AuthRepository): RegisterUseCase{
            return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository:AuthRepository): LoginUseCase{
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthenticationUseCase(repository:AuthRepository): AuthenticateUseCase{
        return AuthenticateUseCase(repository)
    }

}
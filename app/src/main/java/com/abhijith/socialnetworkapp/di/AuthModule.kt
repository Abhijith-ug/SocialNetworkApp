package com.abhijith.socialnetworkapp.di

import com.abhijith.socialnetworkapp.featureauth.data.remote.AuthApi
import com.abhijith.socialnetworkapp.featureauth.data.repository.AuthRepositoryImpl
import com.abhijith.socialnetworkapp.featureauth.domain.repository.AuthRepository
import com.abhijith.socialnetworkapp.featureauth.domain.use_case.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi{
        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api:AuthApi):AuthRepository{
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository:AuthRepository): RegisterUseCase{
            return RegisterUseCase(repository)
    }

}
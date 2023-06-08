package com.abhijith.socialnetworkapp.di

import com.abhijith.socialnetworkapp.core.data.remote.PostApi
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.featureprofile.data.remote.ProfileApi
import com.abhijith.socialnetworkapp.featureprofile.data.repository.ProfileRepositoryImpl
import com.abhijith.socialnetworkapp.featureprofile.domain.repository.ProfileRepository
import com.abhijith.socialnetworkapp.featureprofile.domain.use_case.*
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
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileApi: ProfileApi, postApi: PostApi, gson: Gson): ProfileRepository {
        return ProfileRepositoryImpl(profileApi,postApi,gson)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfileUseCase = GetProfileUseCase(repository),
            getSkillsUseCase = GetSkillsUseCase(repository),
            updateProfileUseCase = UpdateProfileUseCase(repository),
            setSkillSelectedUseCase = SetSkillSelectedUseCase( ),
            getPostsForProfileUseCase = GetPostsForProfileUseCase(repository),
            searchUserUseCase = SearchUserUseCase(repository),
            toggleFollowUseCaseForUserUseCase = ToggleFollowUseCaseForUserUseCase(repository)
        )
    }
}
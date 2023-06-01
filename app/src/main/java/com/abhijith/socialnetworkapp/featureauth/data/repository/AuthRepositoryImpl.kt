package com.abhijith.socialnetworkapp.featureauth.data.repository

import android.content.SharedPreferences
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.core.util.SimpleResource
import com.abhijith.socialnetworkapp.featureauth.data.datasource.remote.request.CreateAccountRequest
import com.abhijith.socialnetworkapp.featureauth.data.datasource.remote.request.LoginRequest
import com.abhijith.socialnetworkapp.featureauth.data.datasource.remote.AuthApi
import com.abhijith.socialnetworkapp.featureauth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {
    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        val request = CreateAccountRequest(email, username, password)
        return try {
            val response = api.register(request)
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(id = R.string.error_unknown))
            }

        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }
    }

    override suspend fun login(email: String, password: String): SimpleResource {
        val request = LoginRequest(email, password)
        return try {
            val response = api.login(request)
            if (response.successful) {
                response.data?.token?.let {
                    token ->
                    sharedPreferences.edit()
                        .putString(Constants.KEY_JWT_TOKEN,token)
                        .apply()
                }
                Resource.Success(Unit)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(id = R.string.error_unknown))
            }

        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }
    }

    override suspend fun authenticate(): SimpleResource {

            return try {
                api.authenticate()
                Resource.Success(Unit)
            } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }
    }
}
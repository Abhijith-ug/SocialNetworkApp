package com.abhijith.socialnetworkapp.featureauth.data.repository

import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.core.util.simpleResource
import com.abhijith.socialnetworkapp.featureauth.data.dto.request.CreateAccountRequest
import com.abhijith.socialnetworkapp.featureauth.data.remote.AuthApi
import com.abhijith.socialnetworkapp.featureauth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(private val api:AuthApi):AuthRepository {
    override suspend fun register(email:String,username:String,password:String): simpleResource {
        val request = CreateAccountRequest(email,username,password)
       return try {
            val response = api.register(request)
            if (response.successful){
                Resource.Success(Unit)
            }else{
                response.message?.let {
                    msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource. Error(UiText.StringResource(id =R.string.error_unknown ))
            }

        } catch (e:IOException){
           Resource. Error(uiText = UiText.StringResource(id =R.string.error_couldnt_reach_server ))
        } catch (e:HttpException){
           Resource.Error(uiText = UiText.StringResource(id = R.string.oops_something_went_wrong))
        }
    }
}
package com.abhijith.socialnetworkapp.core.domain.usecase

import android.content.SharedPreferences
import com.abhijith.socialnetworkapp.core.util.Constants

class GetOwnUserIdUseCase(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke():String{
        return sharedPreferences.getString(Constants.KEY_USER_ID, "") ?:""
    }
}
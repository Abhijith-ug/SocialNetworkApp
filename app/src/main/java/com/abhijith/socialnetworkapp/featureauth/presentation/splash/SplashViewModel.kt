package com.abhijith.socialnetworkapp.featureauth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.Screen
import com.abhijith.socialnetworkapp.featureauth.domain.use_case.AuthenticateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase
):ViewModel(){

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {

            when( authenticateUseCase()){
                is Resource.Success -> {
                   _eventFlow.emit(
                       UiEvent.Navigate(Screen.MainFeedScreen.route)
                   )
                }
                is Resource.Error -> {
                   _eventFlow.emit(
                       UiEvent.Navigate(Screen.LoginScreen.route)
                   )
                }
            }
        }
    }

}
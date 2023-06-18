package com.abhijith.socialnetworkapp.featureauth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.Screen
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featureauth.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase:LoginUseCase
): ViewModel() {


    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState :State<StandardTextFieldState> = _emailState

    private val _passwordState = mutableStateOf(StandardTextFieldState())
    val passwordState :State<StandardTextFieldState> = _passwordState

    private val _loginState = mutableStateOf(LoginState())
    val loginState :State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event:LoginEvent){
        when(event){
            is LoginEvent.EnteredEmail ->{
                 _emailState.value = emailState.value.copy(
                     text = event.email
                 )
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.value = emailState.value.copy(
                    text = event.password
                )
            }
            is LoginEvent.TogglePasswordVisibility ->{
                _loginState.value = loginState.value.copy(
                    isPasswordVisible = !loginState.value.isPasswordVisible
                )
            }
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    _loginState.value = loginState.value.copy(
                        isLoading = true
                    )
                    val loginResult = loginUseCase(
                        email = emailState.value.text,
                        password = passwordState.value.text
                    )
                    _loginState.value = loginState.value.copy(
                        isLoading = false
                    )
                    if (loginResult.emailError != null){
                        _emailState.value = emailState.value.copy(
                            error = loginResult.emailError
                        )
                    }
                    if (loginResult.passwordError != null){
                        _passwordState.value = passwordState.value.copy(
                            error = loginResult.passwordError
                        )
                    }
                    when(loginResult.result){
                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.OnLogin)
                        }
                        is Resource.Error -> {
                             _eventFlow.emit(
                                 UiEvent.ShowSnackBar(loginResult.result.uiText ?: UiText.unKnownError())
                             )
                        }
                        null -> Unit
                    }
                }

            }

            else -> {}
        }
    }





}
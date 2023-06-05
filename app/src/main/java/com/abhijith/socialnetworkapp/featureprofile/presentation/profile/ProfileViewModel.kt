package com.abhijith.socialnetworkapp.featureprofile.presentation.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featureprofile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _toolbarState = mutableStateOf(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
//        savedStateHandle.get<String>("userId")?.let { userId ->
//            Log.d("abii", "User id : $userId")
//            getProfile(userId)
//        }
    }

    fun setExpandedRatio(ratio: Float) {
        _toolbarState.value = _toolbarState.value.copy(expandedRatio = ratio)
    }

    fun setToolbarOffsetY(value: Float) {
        _toolbarState.value = _toolbarState.value.copy(toolbarOffsetY = value)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetProfile -> {
//                getProfile()
            }
        }
    }

    fun getProfile(userId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result = profileUseCases.getProfileUseCase(userId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        profile = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            uiText = result.uiText ?: UiText.unKnownError()
                        )
                    )
                }
            }
        }
    }

}
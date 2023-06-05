package com.abhijith.socialnetworkapp.featurepost.presentation.createpost

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.jvm.internal.Intrinsics.Kotlin

@HiltViewModel
class CreatePostViewModel @Inject constructor(
  private val postUseCases : PostUseCases
) : ViewModel() {

    private val _descriptionState = mutableStateOf(StandardTextFieldState())
    val descriptionState: State<StandardTextFieldState> = _descriptionState

    private val _isLoading = mutableStateOf(false)
    val isLoading : State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImageUri:State<Uri?> = _chosenImageUri


    fun onEvent(event:CreatePostEvent){
        when(event){
            is CreatePostEvent.EnterDescription -> {
                _descriptionState.value = descriptionState.value.copy(
                    text = event.value
                )
            }
            is CreatePostEvent.PickImage -> {
                _chosenImageUri.value = event.uri
            }
            is CreatePostEvent.CropImage -> {
                _chosenImageUri.value = event.uri
            }

            is CreatePostEvent.PostImage -> {

                    viewModelScope.launch {
                        _isLoading.value = true
                  val result  =   postUseCases.createPostUseCase(
                            description = descriptionState.value.text,
                            imageUri = chosenImageUri.value
                        )
                        when(result){
                            is Resource.Success -> {
                                _eventFlow.emit(UiEvent.ShowSnackBar(
                                    uiText = UiText.StringResource(R.string.post_created)
                                ))
                                _eventFlow.emit(UiEvent.NavigateUp)
                            }
                            is Resource.Error -> {
                                 _eventFlow.emit(UiEvent.ShowSnackBar(
                                     result.uiText ?: UiText.unKnownError()
                                 ))
                            }
                        }
                        _isLoading.value = false
                    }



            }

        }
    }
}
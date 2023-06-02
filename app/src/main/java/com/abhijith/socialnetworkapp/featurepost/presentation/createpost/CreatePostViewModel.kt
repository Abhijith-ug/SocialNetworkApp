package com.abhijith.socialnetworkapp.featurepost.presentation.createpost

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
  private val postUseCases : PostUseCases
) : ViewModel() {

    private val _descriptionState = mutableStateOf(StandardTextFieldState())
    val descriptionState: State<StandardTextFieldState> = _descriptionState

    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImageUri:State<Uri?> = _chosenImageUri

      val destUri = Uri.EMPTY

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
                chosenImageUri.value?.let {
                    uri ->
                    viewModelScope.launch {
                        postUseCases.createPostUseCase(
                            description = descriptionState.value.text,
                            imageUri = uri
                        )
                    }

                }

            }

        }
    }
}
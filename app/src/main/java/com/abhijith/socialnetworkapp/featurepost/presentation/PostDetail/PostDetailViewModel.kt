package com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("postId")?.let {
            postId ->
            loadPostDetails(postId)
            loadCommentsForPost(postId)
        }
    }

    fun onEvent(event: PostDetailEvent){
        when(event){
            is PostDetailEvent.LikePost -> {

            }
            is PostDetailEvent.Comment -> {

            }
            is PostDetailEvent.LikeComment -> {

            }
            is PostDetailEvent.SharePost -> {

            }
        }
    }

    private fun loadPostDetails(postId:String){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            Log.d("POSTID", "${postId} ")
            val result = postUseCases.getPostDetailsUseCase(postId)
            when(result){
                is Resource.Success -> {
                    Log.d("abid", "${result.data}")
                    _state.value = state.value.copy(
                        post = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            result.uiText?: UiText.unKnownError()
                        )
                    )
                }
            }
        }
    }

    private fun loadCommentsForPost(postId:String){
        viewModelScope.launch {
             _state.value = state.value.copy(
                 isLoadingComments = true
             )
            val result = postUseCases.getCommentsForPostUseCase(postId)
            when(result){
                is Resource.Success ->{
                    _state.value = state.value.copy(
                        comments = result.data?: emptyList(),
                        isLoadingComments = false
                    )
                }
                is Resource.Error ->{
                    _state.value = state.value.copy(
                        isLoadingComments = false
                    )
                }
            }
        }
    }

}
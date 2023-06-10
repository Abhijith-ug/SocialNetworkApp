package com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.ParentType
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.PostUseCases
import com.abhijith.socialnetworkapp.featurepost.util.CommentError
import com.abhijith.socialnetworkapp.featurepost.util.PostDescriptionError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
   private val savedStateHandle: SavedStateHandle
):ViewModel(){

    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _commentTextFieldState = mutableStateOf(StandardTextFieldState(error = CommentError.FieldEmpty))
    val commentTextFieldState:State<StandardTextFieldState> = _commentTextFieldState

    private val _commentState = mutableStateOf(CommentState())
    val commentState: State<CommentState> = _commentState

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
                val isLiked = state.value.post?.isLiked == true
                toggleLikeForParent(
                    parentId = state.value.post?.id ?: return,
                    parentType =ParentType.Post.type,
                    isLiked = isLiked
                )
            }
            is PostDetailEvent.Comment -> {
                 createComment(
                     postId = savedStateHandle.get<String>("postId") ?:"",
                     comment = commentTextFieldState.value.text
                 )
            }
            is PostDetailEvent.LikeComment -> {
                val isLiked =  state.value.comments.find {
                    it.id == event.commentId
                }?.isLiked == true
                toggleLikeForParent(
                    parentId = event.commentId,
                    parentType =ParentType.Comment.type,
                    isLiked = isLiked
                )
            }
            is PostDetailEvent.SharePost -> {

            }
            is PostDetailEvent.EnteredComment ->{
                _commentTextFieldState.value = commentTextFieldState.value.copy(
                    text = event.comment,
                    error = if (event.comment.isBlank()) CommentError.FieldEmpty else null
                )
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

    private fun toggleLikeForParent(parentId:String,parentType:Int,isLiked:Boolean){
        viewModelScope.launch {
            when(parentType){
                ParentType.Post.type -> {
                    _state.value = state.value.copy(
                        post = state.value.post?.copy(
                            isLiked = !isLiked
                        )
                    )
                }
                ParentType.Comment.type -> {
                        _state.value = state.value.copy(
                            comments = state.value.comments.map {
                                if (it.id == parentId){
                                    it.copy(
                                        isLiked = !isLiked
                                    )
                                }else it
                            }
                        )
                }
            }
            val result = postUseCases.toggleLikeForParentUseCase(
                parentId,parentType,
                isLiked = isLiked
            )
            when(result){
                is Resource.Success -> Unit

                is Resource.Error -> {
                    when(parentType){
                        ParentType.Post.type -> {
                            _state.value = state.value.copy(
                                post = state.value.post?.copy(
                                    isLiked = isLiked
                                )
                            )
                        }
                        ParentType.Comment.type -> {
                            _state.value = state.value.copy(
                                comments = state.value.comments.map {
                                    if (it.id == parentId){
                                        it.copy(
                                            isLiked = !isLiked
                                        )
                                    }else it
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun createComment(postId: String,comment:String){
        viewModelScope.launch {
           _commentState.value = commentState.value.copy(
               isLoading = true
           )
            delay(3000L)
            val result = postUseCases.createCommentUseCase(
                postId = postId,
                comment = comment
            )
            when(result){
                is Resource.Success -> {
                  _eventFlow.emit( UiEvent.ShowSnackBar(
                      uiText = UiText.StringResource(R.string.comment_posted)
                  ))
                    _commentState.value = commentState.value.copy(
                        isLoading = false
                    )
                    _commentTextFieldState.value = commentTextFieldState.value.copy(
                        text = ""
                    )
                    loadCommentsForPost(postId)
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackBar(
                            uiText = result.uiText?: UiText.unKnownError()
                        )
                    )
                    _commentState.value = commentState.value.copy(
                        isLoading = false
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
package com.abhijith.socialnetworkapp.featureprofile.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.domain.usecase.GetOwnUserIdUseCase
import com.abhijith.socialnetworkapp.core.presentation.PagingState
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.*
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.PostUseCases
import com.abhijith.socialnetworkapp.featureprofile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val postUseCases: PostUseCases,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val postLiker: PostLiker
) : ViewModel() {

    private val _toolbarState = mutableStateOf(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

//    val posts = profileUseCases.getPostsForProfileUseCase(
//        savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
//    ).cachedIn(viewModelScope)

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState


    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = {
            page ->
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserIdUseCase()
            profileUseCases.getPostsForProfileUseCase(userId = userId, page = page)
        },
        onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(
                UiEvent.ShowSnackBar(
                    uiText
                )
            )
        }
    )

    fun setExpandedRatio(ratio: Float) {
        _toolbarState.value = _toolbarState.value.copy(expandedRatio = ratio)
    }

    fun setToolbarOffsetY(value: Float) {
        _toolbarState.value = _toolbarState.value.copy(toolbarOffsetY = value)
    }

    init {
        loadNextPosts()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetProfile -> {
//                getProfile()
            }
            is ProfileEvent.LikePost -> {
                viewModelScope.launch {
                    toggleLikeForParent(
                        parentId = event.postId
                    )
                }

            }
        }
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
            }
        }


    private fun toggleLikeForParent(parentId: String) {
        viewModelScope.launch {
            postLiker.toggleLike(
                posts = pagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.toggleLikeForParentUseCase(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked
                    )
                },
                onStateUpdated = { posts ->
                    _pagingState.value = pagingState.value.copy(
                        items = posts
                    )
                }
            )
        }
    }

    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result = profileUseCases.getProfileUseCase(userId ?: getOwnUserIdUseCase())) {
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
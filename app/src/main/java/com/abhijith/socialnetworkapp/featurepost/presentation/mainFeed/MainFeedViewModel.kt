package com.abhijith.socialnetworkapp.featurepost.presentation.mainFeed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.presentation.PagingState
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.*
import com.abhijith.socialnetworkapp.featurepost.data.datasource.pagesource.PostSource
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.PostUseCases
import com.abhijith.socialnetworkapp.featurepost.presentation.personlist.PostEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject
@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val postLiker: PostLiker
) :ViewModel(){



    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState


    private val paginator = DefaultPaginator(
        onLoadUpdated = {
                        isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )

        },
        onRequest = {page ->
            postUseCases.getPostsForFollowUseCase(
                page = page
            )

        },
        onSuccess = {posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )

        },
        onError = {
            uiText ->
            _eventFlow.emit(
                UiEvent.ShowSnackBar(
                    uiText
                )
            )
        }
    )

    init {
        loadNextPost()
    }

    fun onEvent(event:MainFeedEvents){
        when(event){
            is MainFeedEvents.LikedPost -> {
              toggleLikeForParent(
                  event.postId
              )
            }

        }
    }

    fun loadNextPost(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun toggleLikeForParent(parentId:String){
        viewModelScope.launch {
          postLiker.toggleLike(
              posts = pagingState.value.items,
              parentId = parentId,
              onRequest = {isLiked ->
                  postUseCases.toggleLikeForParentUseCase(
                      parentId = parentId,
                      parentType = ParentType.Post.type,
                      isLiked = isLiked

                  )
              },
              onStateUpdated = {
                  posts ->
                  _pagingState.value = pagingState.value.copy(
                      items = posts
                  )
              }
          )
        }
    }
}
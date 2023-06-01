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
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.featurepost.data.datasource.pagesource.PostSource
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Flow
import javax.inject.Inject
@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) :ViewModel(){


    private val _state = mutableStateOf(MainFeedState())
    val state : State<MainFeedState> = _state

  val posts = postUseCases.getPostsForFollowUseCase()
      .cachedIn(viewModelScope)

    fun onEvent(event:MainFeedEvents){
        when(event){
            is MainFeedEvents.LoadMorePosts -> {
                _state.value = state.value.copy(
                    isLoadingNewPosts = true
                )
            }
            is MainFeedEvents.LoadedPage -> {
                _state.value = state.value.copy(
                    isLoadingFirstTime = false,
                    isLoadingNewPosts = false
                )
            }
        }
    }
}
package com.abhijith.socialnetworkapp.featureprofile.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featureprofile.domain.use_case.ProfileUseCases
import com.abhijith.socialnetworkapp.featureprofile.domain.util.ProfileConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
):ViewModel(){

    private val _searchFieldState = mutableStateOf(StandardTextFieldState())
    val searchFieldState : State<StandardTextFieldState> = _searchFieldState

    private val  _searchState = mutableStateOf(SearchState())
    val searchState : State<SearchState> = _searchState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.Query ->{
              searchUser(event.query)
            }
            is SearchEvent.ToggleFollowState ->{
               toggleFollowStateForUser(event.userId)
            }
        }
    }

    private fun toggleFollowStateForUser(userId:String){
       viewModelScope.launch {



           val isFollowing = searchState.value.userItems.find {
               it.userId == userId
           }?.isFollowing == true

           _searchState.value = searchState.value.copy(
               userItems = searchState.value.userItems.map {
                   if (it.userId == userId){
                       it.copy(isFollowing = !it.isFollowing)
                   }else it
               }
           )
           val result = profileUseCases.toggleFollowUseCaseForUserUseCase(
               userId = userId,
               isFollowing = isFollowing
           )
           when(result){
               is Resource.Success -> Unit
               is Resource.Error -> {
                   _searchState.value = searchState.value.copy(
                       userItems = searchState.value.userItems.map {
                           if (it.userId == userId){
                               it.copy(isFollowing = isFollowing)
                           }else it
                       }
                   )
                   _eventFlow.emit(UiEvent.ShowSnackBar(
                       uiText = result.uiText?: UiText.unKnownError()
                   ))
               }
           }
       }
    }

    private fun searchUser(query:String){
        _searchFieldState.value = searchFieldState.value.copy(
            text = query
        )
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(ProfileConstants.SEARCH_DELAY)
            _searchState.value = searchState.value.copy(
                isLoading = true
            )
            val result = profileUseCases.searchUserUseCase(query)
            when(result){
                is Resource.Success -> {
                    _searchState.value = searchState.value.copy(
                        userItems = result.data?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _searchFieldState.value = searchFieldState.value.copy(
                        error = SearchError(
                            message = result.uiText?:UiText.unKnownError()
                        )
                    )
                    _searchState.value = searchState.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }



}
package com.abhijith.socialnetworkapp.featurepost.presentation.personlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.socialnetworkapp.core.domain.usecase.GetOwnUserIdUseCase
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.Resource
import com.abhijith.socialnetworkapp.core.util.UiText
import com.abhijith.socialnetworkapp.featurepost.domain.use_case.PostUseCases
import com.abhijith.socialnetworkapp.core.domain.usecase.ToggleFollowUseCaseForUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val toggleFollowUseCaseForUserUseCase: ToggleFollowUseCaseForUserUseCase,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel() {


    private val _state = mutableStateOf(PersonListState())
    val state: State<PersonListState> = _state

    private val _ownUserId = mutableStateOf("")
    val ownUserId :State<String> = _ownUserId

    private val _eventFlow  = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("parentId")?.let {
            parentId ->
            getLikesForParent(parentId)
            _ownUserId.value = getOwnUserIdUseCase()

        }
    }

    fun onEvent(event: PersonListEvent){
        when(event){
            is PersonListEvent.ToggleFollowStateForUser -> {
               toggleFollowStateForUser(event.userId)
            }
        }
    }

    private fun getLikesForParent(parentId:String){
        viewModelScope.launch {
               _state.value = state.value.copy(
                   isLoading = true
               )
            val result = postUseCases.getLikesForParentUseCase(parentId)
            when(result){
                is Resource.Success ->{
                  _state.value = state.value.copy(
                      users = result.data?: emptyList(),
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

    private fun toggleFollowStateForUser(userId:String){
        viewModelScope.launch {



            val isFollowing = state.value.users.find {
                it.userId == userId
            }?.isFollowing == true

            _state.value = state.value.copy(
                users = state.value.users.map {
                    if (it.userId == userId){
                        it.copy(isFollowing = !it.isFollowing)
                    }else it
                }
            )
            val result = toggleFollowUseCaseForUserUseCase(
                userId = userId,
                isFollowing = isFollowing
            )
            when(result){
                is Resource.Success -> Unit
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        users = state.value.users.map {
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

}
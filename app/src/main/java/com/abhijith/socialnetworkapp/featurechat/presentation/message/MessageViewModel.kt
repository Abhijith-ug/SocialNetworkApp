package com.abhijith.socialnetworkapp.featurechat.presentation.message

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor():ViewModel(){

    private val _messageTextFieldState = mutableStateOf(StandardTextFieldState())
    val messageTextFieldState : State<StandardTextFieldState> = _messageTextFieldState

    private val _state =  mutableStateOf(MessageState())
    val state: State<MessageState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: MessageEvent){
       when(event){
           is MessageEvent.EnteredMessage -> {
             _messageTextFieldState.value = messageTextFieldState.value.copy(
                 text = event.message
             )
           }
           is MessageEvent.SendMessage -> {

           }
       }
    }
}
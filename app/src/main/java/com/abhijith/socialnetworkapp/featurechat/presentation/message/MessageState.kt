package com.abhijith.socialnetworkapp.featurechat.presentation.message

import com.abhijith.socialnetworkapp.featurechat.domain.model.Message

data class MessageState(
    val messages:List<Message> = emptyList(),
    val isLoading:Boolean = false
)

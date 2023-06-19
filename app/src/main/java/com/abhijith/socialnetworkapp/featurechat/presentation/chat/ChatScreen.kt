package com.abhijith.socialnetworkapp.presentation.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.core.util.Screen
import com.abhijith.socialnetworkapp.featurechat.domain.model.Chat
import com.abhijith.socialnetworkapp.featurechat.presentation.ChatItem

@SuppressLint("RememberReturnType")
@Composable
fun ChatScreen(
    onNavigate: (String) -> Unit = { },
    onNavigateUp: () -> Unit = {},
) {

    val chats = remember {
        listOf(
            Chat(
                remoteUsername = "abhijth",
                remoteUserProfileUrl = "http://192.168.64.137:8001/profile_pictures/a20005ea-0cd1-484a-bc58-f5f3770e1d95.jpeg",
                lastMessage = "This is the last message of the chat",
                lastMessageFormattedTime = "19:39"
            ),
            Chat(
                remoteUsername = "abhijth",
                remoteUserProfileUrl = "http://192.168.64.137:8001/profile_pictures/a20005ea-0cd1-484a-bc58-f5f3770e1d95.jpeg",
                lastMessage = "This is the last message of the chat",
                lastMessageFormattedTime = "19:39"
            ),
            Chat(
                remoteUsername = "abhijth",
                remoteUserProfileUrl = "http://192.168.64.137:8001/profile_pictures/a20005ea-0cd1-484a-bc58-f5f3770e1d95.jpeg",
                lastMessage = "This is the last message of the chat",
                lastMessageFormattedTime = "19:39"
            )


        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceMedium),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(chats) { chat ->
                ChatItem(item = chat, onItemClick = {
                    onNavigate(Screen.MessageScreen.route)
                })
                Spacer(modifier = Modifier.height(SpaceLarge))
            }
        }
    }
}

package com.abhijith.socialnetworkapp.featurechat.presentation.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.presentation.components.SendTextField
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.profilePictureSizeMedium
import com.abhijith.socialnetworkapp.featurechat.domain.model.Chat
import com.abhijith.socialnetworkapp.featurechat.domain.model.Message
import com.abhijith.socialnetworkapp.featurechat.presentation.message.components.OwnMessage
import com.abhijith.socialnetworkapp.featurechat.presentation.message.components.RemoteMessage
import com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail.PostDetailEvent

@Composable
fun MessageScreen(
    chatId: String,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    viewModel: MessageViewModel = hiltViewModel()
) {
    val messages = remember {
        listOf(
            Message(
                fromId = "",
                toId = "",
                text = "hello World! ",
                formattedTime = "19:34",
                chatId = "",
                id = ""
            ),
            Message(
                fromId = "",
                toId = "",
                text = "hello World! ",
                formattedTime = "19:34",
                chatId = "",
                id = ""
            ),
            Message(
                fromId = "",
                toId = "",
                text = "hello World! ",
                formattedTime = "19:34",
                chatId = "",
                id = ""
            ),
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {

        StandardToolbar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Image(
                    painter = rememberImagePainter(data = "http://192.168.64.137:8001/profile_pictures/a20005ea-0cd1-484a-bc58-f5f3770e1d95.jpeg",
                        builder = {
                            crossfade(true)
                        }),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(profilePictureSizeMedium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(SpaceMedium))
                Text(
                    text = "abhijith",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            }
        )
        Column(modifier = Modifier.weight(1f)) {
            LazyColumn(modifier = Modifier
                .weight(1f)
                .padding(SpaceMedium)) {
                items(messages) { message ->
                    RemoteMessage(
                        message = message.text,
                        formattedTime = message.formattedTime,
                        color = MaterialTheme.colors.surface
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    OwnMessage( message = message.text,
                        formattedTime = message.formattedTime,
                        color = MaterialTheme.colors.primary)
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
            SendTextField(state = viewModel.messageTextFieldState.value, onValueChange = {
                viewModel.onEvent(MessageEvent.EnteredMessage(it))
            }, onSend = {
                viewModel.onEvent(MessageEvent.SendMessage)
            },
                hint = stringResource(id = R.string.enter_a_message)
            )
        }

    }
}

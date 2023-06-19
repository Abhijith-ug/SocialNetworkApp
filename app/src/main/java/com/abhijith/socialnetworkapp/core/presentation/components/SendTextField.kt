package com.abhijith.socialnetworkapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail.PostDetailEvent

@Composable
fun SendTextField(
    state:StandardTextFieldState,
    onValueChange:(String) -> Unit,
    onSend:() -> Unit,
    hint:String = "",
    isLoading:Boolean = false,
    focusRequester:FocusRequester = FocusRequester()
){
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .padding(SpaceLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StandardTextField(
            text = state.text,
            hint = hint,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            focusRequester = focusRequester
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(24.dp),
                strokeWidth = 2.dp

            )
        } else {
            IconButton(
                onClick =  onSend ,
                enabled = state.error == null
            ) {

                Icon(
                    imageVector = Icons.Default.Send, tint =
                    if (state.error == null) {
                        MaterialTheme.colors.primary
                    } else MaterialTheme.colors.background,
                    contentDescription = stringResource(id = R.string.send_comment)
                )

            }
        }

    }
}
package com.abhijith.socialnetworkapp.featurechat.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceSmall
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.profilePictureSizeMedium
import com.abhijith.socialnetworkapp.featurechat.domain.model.Chat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatItem(
    item:Chat,
    modifier: Modifier = Modifier,
    onItemClick:(Chat) -> Unit
){
    Card(
        modifier = modifier, shape =
        MaterialTheme.shapes.medium,
        onClick = {
                  onItemClick(item)
        },
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberImagePainter(data = item.remoteUserProfileUrl,
                    builder = {
                        crossfade(true)
                    }),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(profilePictureSizeMedium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(SpaceSmall))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = SpaceSmall)
                    .weight(1f)
            ) {
                Row (
                    modifier = Modifier.fillMaxSize()){
                    Text(
                        text = item.remoteUsername, style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(1f )
                    )
                    Spacer(modifier =modifier.width(SpaceSmall))
                    Text(text = item.lastMessageFormattedTime)
                }

                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = item.lastMessage, style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.heightIn(
                        min = MaterialTheme.typography.body2.fontSize.value.dp * 2.5f)
                )
            }

        }
    }
}

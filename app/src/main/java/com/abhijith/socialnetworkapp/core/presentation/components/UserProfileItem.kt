package com.abhijith.socialnetworkapp.core.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.IconSizeMedium
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceSmall
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.profilePictureSizeBtwSmallMed
import com.abhijith.socialnetworkapp.core.domain.models.User
import com.abhijith.socialnetworkapp.core.domain.models.UserItem
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.profilePictureSizeMedium
import com.abhijith.socialnetworkapp.core.util.Constants

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun  UserProfileItem(
    user: UserItem,
    modifier: Modifier = Modifier,
    actionIcon: @Composable () -> Unit = {},
    onItemClick: () -> Unit = {},
    onActionItemClick: () -> Unit = {},
    ownUserId:String = ""
) {
    Card(
        modifier = modifier, shape =
        MaterialTheme.shapes.medium,
        onClick = onItemClick,
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
                painter = rememberImagePainter(data = user.profilePictureUrl,
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
                    .weight(1f)
            ) {
                Text(
                    text = user.username, style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = user.bio, style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(SpaceSmall))
            if (user.userId != ownUserId){
                IconButton(
                    onClick = { onActionItemClick },
                    modifier = Modifier.size(IconSizeMedium)
                ) {
                    actionIcon()
                }
            }

        }
    }
}
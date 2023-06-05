package com.abhijith.socialnetworkapp.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.*
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Constants

@Composable
fun Post(
    post: Post,
    modifier: Modifier = Modifier,
    showProfileImage: Boolean = true,
    onPostClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(SpaceMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = if (showProfileImage) profilePictureSizeMedium / 2f else 0.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MediumGray)
                .clickable {
                    onPostClick()
                }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = post
                        .imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ), contentDescription = "post image"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceMedium)
            ) {
                Spacer(modifier = Modifier.height(SpaceMedium))
                ActionRow(modifier = Modifier.fillMaxWidth(), username = "Abhijith",
                    onLikeClick = { isLiked ->

                    },
                    onCommentClick = {

                    },
                    onShareClick = {

                    },
                    onUsernameClick = { username ->
                    })
                Spacer(modifier = Modifier.height(SpaceSmall))

                Text(
                    text = buildAnnotatedString {
                        append(post.description)
                        withStyle(
                            SpanStyle(
                                color = HintGray
                            )
                        ) {
                            append(
                                LocalContext.current.getString(
                                    R.string.read_more
                                )
                            )
                        }
                    },
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = Constants.MAX_POST_DESCRIPTION_LINES
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.liked_by_x_people,

                            post.likeCount
                        ), fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h2
                    )
                    Text(
                        text = stringResource(
                            id = R.string.x_comments,
                            post.commentCount
                        ), fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2
                    )

                }
            }

        }
        if (showProfileImage) {
            Image(
                painter = rememberImagePainter(data = post.profilePictureUrl,
                builder = {
                    crossfade(true)
                }), contentDescription = "profile picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(profilePictureSizeMedium)
                    .align(Alignment.TopCenter), contentScale = ContentScale.Crop
            )
        }


    }

}

@Composable
fun EngagementButtons(
    isLiked: Boolean = false,
    iconSize: Dp = 30.dp,
    onLikeClick: (Boolean) -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onLikeClick(!isLiked)
        }, modifier = Modifier.size(iconSize)) {
            Icon(
                imageVector = Icons.Filled.Favorite, tint =
                if (isLiked) {
                    Color.Red
                } else {
                    TextWhite
                }, contentDescription =
                if (isLiked) {
                    stringResource(id = R.string.unlike)
                } else {
                    stringResource(id = R.string.like)
                }
            )

        }
        Spacer(modifier = Modifier.width(SpaceMedium))

        IconButton(onClick = {
            onCommentClick()
        }, modifier = Modifier.size(iconSize)) {
            Icon(
                imageVector = Icons.Filled.Comment, contentDescription =
                stringResource(id = R.string.comment)
            )

        }
        Spacer(modifier = Modifier.width(SpaceMedium))

        IconButton(onClick = {
            onShareClick()
        }, modifier = Modifier.size(iconSize)) {
            Icon(
                imageVector = Icons.Filled.Share, contentDescription =
                stringResource(id = R.string.share)
            )

        }
    }

}

@Composable
fun ActionRow(
    modifier: Modifier,
    isLiked: Boolean = false,
    onLikeClick: (Boolean) -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    username: String,
    onUsernameClick: (String) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = username, style = TextStyle(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        ), modifier = Modifier.clickable {
            onUsernameClick(username)
        })
        EngagementButtons(
            isLiked = isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick
        )

    }
}
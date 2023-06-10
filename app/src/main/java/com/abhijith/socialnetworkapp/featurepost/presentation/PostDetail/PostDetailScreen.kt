package com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.Comment
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.presentation.components.ActionRow
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.*
import com.abhijith.socialnetworkapp.core.util.Constants

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PostDetailScreen(
    onNavigate: (String) -> Unit = { },
    onNavigateUp: () -> Unit = {},
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
        )
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = profilePictureSizeMedium / 2f)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MediumGray)
            ) {
                state.post?.let { post ->
                    Image(
                        painter = rememberImagePainter(
                            data = "${Constants.BASE_URL}" + post.imageUrl, builder = {
                                crossfade(true)
                            }),
                        contentDescription = "post image",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SpaceLarge)
                    ) {
                        Spacer(modifier = Modifier.height(SpaceMedium))
                        ActionRow(
                            modifier = Modifier.fillMaxWidth(),
                            username = "Abhijith",
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
                            text = post.description,
                            style = MaterialTheme.typography.body2,
                        )
                        Spacer(modifier = Modifier.height(SpaceMedium))

                        Text(
                            text = stringResource(
                                id = R.string.liked_by_x_people,

                                post.likeCount
                            ), fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.comments) {
                        comment ->
                        Comment(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = SpaceLarge,
                                    vertical = SpaceSmall
                                ),
                            comment = comment
                        )
                    }
                }
            }
            Image(
                painter = rememberImagePainter(data =
                "${Constants.BASE_URL}"+
                state.post?.profilePictureUrl, builder = {
                    crossfade(true)
                }),
                contentDescription = "profilePicture",
                modifier = Modifier
                    .size(profilePictureSizeMedium)
                    .clip(CircleShape)
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.Crop
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
    }
}

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment  ,
    onLikeClick: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 5.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(data =
                        comment.profilePictureUrl, builder = {
                            crossfade(true)
                        }),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(profilePictureSizeSmall)
                    )
                    Spacer(modifier = Modifier.width(SpaceSmall))
                    Text(
                        text = comment.username,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Text(
                    text = comment.formattedTime,
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = comment.comment,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.weight(9f)
                )
                Spacer(modifier = Modifier.width(SpaceMedium))
                IconButton(
                    onClick = {
                        onLikeClick(comment.isLiked)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = if (comment.isLiked){
                                                   MaterialTheme.colors.primary
                                                   }else{
                                                        MaterialTheme.colors.onBackground
                                                        },
                        contentDescription = if (comment.isLiked) {
                            stringResource(id = R.string.unlike)
                        } else stringResource(id = R.string.like)
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            Text(
                text = stringResource(id = R.string.liked_by_x_people, comment.likeCount),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

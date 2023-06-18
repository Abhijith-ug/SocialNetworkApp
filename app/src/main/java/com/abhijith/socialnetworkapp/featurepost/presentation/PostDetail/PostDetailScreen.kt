package com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.abhijith.socialnetworkapp.core.presentation.components.StandardTextField
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.*
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.presentation.util.asString
import com.abhijith.socialnetworkapp.core.presentation.util.showKeyboard
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Screen
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PostDetailScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PostDetailViewModel = hiltViewModel(),
    shouldShowKeyboard:Boolean = false
) {
    val state = viewModel.state.value
    val commentTextFieldState = viewModel.commentTextFieldState.value
    val focusRequester = remember{
        FocusRequester()
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        if (shouldShowKeyboard){
            context.showKeyboard()
            focusRequester.requestFocus()
        }
        viewModel.eventFlow.collectLatest {
            event ->
            when(event){
              is UiEvent.ShowSnackBar -> {
                   scaffoldState.snackbarHostState.showSnackbar(
                       message = event.uiText.asString(context)
                   )
              }
                else -> null
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colors.surface),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                ) {
                    Spacer(modifier = Modifier.height(SpaceLarge))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = profilePictureSizeMedium / 2f)
                                .clip(MaterialTheme.shapes.medium)
                                .shadow(5.dp)
                                .background(MediumGray)
                        ) {
                            state.post?.let { post ->
                                Log.d("image", "${post.imageUrl}: ")
                                Image(
                                    painter = rememberImagePainter(
                                        data = "${Constants.BASE_URL}" + post.imageUrl,
                                        builder = {
                                            crossfade(true)
                                        }
                                    ),
                                    contentDescription = "Post image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1.77f)
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(SpaceLarge)
                                ) {
                                    ActionRow(
                                        username = state.post.username,
                                        modifier = Modifier.fillMaxWidth(),
                                        onLikeClick = {
                                                     viewModel.onEvent(PostDetailEvent.LikePost)

                                        },
                                        onCommentClick = {
                                            context.showKeyboard()
                                            focusRequester.requestFocus()
                                        },
                                        onShareClick = {

                                        },
                                        onUsernameClick = {
                                                          onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}")

                                        },
                                        isLiked = state.post.isLiked
                                    )
                                    Spacer(modifier = Modifier.height(SpaceSmall))
                                    Text(
                                        text = state.post.description,
                                        style = MaterialTheme.typography.body2,
                                    )
                                    Spacer(modifier = Modifier.height(SpaceMedium))
                                    Text(
                                        text = stringResource(
                                            id = R.string.x_likes,
                                            post.likeCount
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.body2,
                                        modifier = Modifier.clickable {
                                            onNavigate(
                                                Screen.PersonalListScreen.route + "/${post.id}"
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        Image(
                            painter = rememberImagePainter(
                                data = "${Constants.BASE_URL}" + state.post?.profilePictureUrl,
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(profilePictureSizeMedium)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter)
                        )
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(SpaceLarge))
            }
            items(state.comments) { comment ->
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = SpaceLarge,
                            vertical = SpaceSmall
                        ),
                    comment = comment,
                    onLikeClick = {
                        viewModel.onEvent(PostDetailEvent.LikeComment(comment.id))
                    },
                    onLikedByClick = {
                        onNavigate(Screen.PersonalListScreen.route + "/${comment.id}")
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()
                .padding(SpaceLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StandardTextField(
                text = commentTextFieldState.text,
                hint = "comment...",
                onValueChange = {
                    viewModel.onEvent(PostDetailEvent.EnteredComment(it))
                },
                modifier = Modifier.weight(1f),
                focusRequester = focusRequester
            )
            if (viewModel.commentState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(24.dp),
                    strokeWidth = 2.dp

                )
            } else {
                IconButton(
                    onClick = { viewModel.onEvent(PostDetailEvent.Comment) },
                    enabled = commentTextFieldState.error == null
                ) {

                    Icon(
                        imageVector = Icons.Default.Send, tint =
                        if (commentTextFieldState.error == null) {
                            MaterialTheme.colors.primary
                        } else MaterialTheme.colors.background,
                        contentDescription = stringResource(id = R.string.send_comment)
                    )

                }
            }

        }
    }
}








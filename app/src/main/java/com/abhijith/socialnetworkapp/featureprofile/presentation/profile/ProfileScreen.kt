package com.abhijith.socialnetworkapp.featureprofile.presentation.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.domain.models.User
import com.abhijith.socialnetworkapp.core.presentation.components.Post
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceSmall
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.profilePictureSizeLarge
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.presentation.util.asString
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Screen
import com.abhijith.socialnetworkapp.core.util.sendSharePostIntent
import com.abhijith.socialnetworkapp.core.util.toPx
import com.abhijith.socialnetworkapp.featurepost.presentation.personlist.PostEvent
import com.abhijith.socialnetworkapp.featureprofile.presentation.profile.components.BannerSection
import com.abhijith.socialnetworkapp.featureprofile.presentation.profile.components.ProfileHeaderSection
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    scaffoldState: ScaffoldState,
    userId: String? = null,
    onNavigate: (String) -> Unit = { },
    onLogout: () -> Unit = {},
    profilePictureSize: Dp = profilePictureSizeLarge,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val pagingState = viewModel.pagingState.value

    val lazyListState = rememberLazyListState()
    val toolbarState = viewModel.toolbarState.value

    val iconHorizontalCenterLength =
        (LocalConfiguration.current.screenWidthDp.dp.toPx() / 4f -
                (profilePictureSize / 4f).toPx() -
                SpaceSmall.toPx()) / 2f
    val iconSizeExpanded = 35.dp
    val toolbarHeightCollapsed = 75.dp
    val imageCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - profilePictureSize / 2f) / 2f
    }
    val iconCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - iconSizeExpanded) / 2f
    }
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    val toolbarHeightExpanded = remember {
        bannerHeight + profilePictureSize
    }
    val maxOffset = remember {
        toolbarHeightExpanded - toolbarHeightCollapsed
    }
    val state = viewModel.state.value
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if (delta > 0f && lazyListState.firstVisibleItemIndex != 0) {
                    return Offset.Zero
                }
                val newOffset = viewModel.toolbarState.value.toolbarOffsetY + delta
                viewModel.setToolbarOffsetY(
                    newOffset.coerceIn(
                        minimumValue = -maxOffset.toPx(),
                        maximumValue = 0f
                    )
                )
                viewModel.setExpandedRatio((viewModel.toolbarState.value.toolbarOffsetY + maxOffset.toPx()) / maxOffset.toPx())
                return Offset.Zero
            }
        }
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.setExpandedRatio(1f)
        viewModel.getProfile(userId)
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is PostEvent.onLiked -> {

                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState
        ) {
            item {
                Spacer(
                    modifier = Modifier.height(
                        toolbarHeightExpanded - profilePictureSize / 2f
                    )
                )
            }
            item {
                state.profile?.let { profile ->
                    ProfileHeaderSection(
                        user = User(
                            userId = profile.userId,
                            profilePictureUrl = profile.profilePictureUrl,
                            username = profile.username,
                            description = profile.bio,
                            followerCount = profile.followerCount,
                            followingCount = profile.followingCount,
                            postCount = profile.postCount,
                        ),
                        isFollowing = profile.isFollowing,
                        isOwnProfile = profile.isOwnProfile,
                        onLogoutClick = {
                            viewModel.onEvent(ProfileEvent.ShowLogoutDialog)
                        },
                        onEditClick = { onNavigate(Screen.EditProfileScreen.route + "/${profile.userId}") },
                    )
                }
            }
            items(pagingState.items.size) { i ->
                val post = pagingState.items[i].copy(
                    username = pagingState.items[i].username,
                    imageUrl = Constants.BASE_URL + pagingState.items[i].imageUrl,
                    profilePictureUrl = Constants.BASE_URL + pagingState.items[i].imageUrl
                )
                Log.d("PAGINGPOST", "${post}")
                if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                    viewModel.loadNextPosts()
                }
                Spacer(
                    modifier = Modifier
                        .height(SpaceSmall)
                )
                Post(
                    post = post,
                    showProfileImage = false,
                    onPostClick = {
                        onNavigate(Screen.PostDetailScreen.route + "/${post.id}")
                    },
                    onLikeClick = {
                        viewModel.onEvent(ProfileEvent.LikePost(post.id))
                    },
                    onCommentClick = {
                        onNavigate(Screen.PostDetailScreen.route + "/${post.id}?shouldShowKeyboard=true")

                    },
                    onShareClick = {
                        context.sendSharePostIntent(post.id)
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            state.profile?.let { profile ->
                Log.d("GIT", "${profile.gitHubUrl}")
                BannerSection(
                    modifier = Modifier
                        .height(
                            (bannerHeight * toolbarState.expandedRatio).coerceIn(
                                minimumValue = toolbarHeightCollapsed,
                                maximumValue = bannerHeight
                            )
                        ),
                    leftIconModifier = Modifier
                        .graphicsLayer {
                            translationY = (1f - toolbarState.expandedRatio) *
                                    -iconCollapsedOffsetY.toPx()
                            translationX = (1f - toolbarState.expandedRatio) *
                                    iconHorizontalCenterLength
                        },
                    rightIconModifier = Modifier
                        .graphicsLayer {
                            translationY = (1f - toolbarState.expandedRatio) *
                                    -iconCollapsedOffsetY.toPx()
                            translationX = (1f - toolbarState.expandedRatio) *
                                    -iconHorizontalCenterLength
                        },
                    topSkills = profile.topSkills ?: emptyList(),
                    shouldShowGitHub = profile.gitHubUrl != null && profile.gitHubUrl.isNotBlank(),
                    shouldShowInstagram = profile.instagramUrl != null && profile.instagramUrl.isNotBlank(),
                    shouldShowLinkedIn = profile.linkedInUrl != null && profile.linkedInUrl.isNotBlank(),
                    bannerUrl = profile.bannerUrl
                )
                Image(
                    painter = rememberImagePainter(data = profile.profilePictureUrl),
                    contentDescription = stringResource(id = R.string.profile),
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .graphicsLayer {
                            translationY = -profilePictureSize.toPx() / 2f -
                                    (1f - toolbarState.expandedRatio) * imageCollapsedOffsetY.toPx()
                            transformOrigin = TransformOrigin(
                                pivotFractionX = 0.5f,
                                pivotFractionY = 0f
                            )
                            val scale = 0.5f + toolbarState.expandedRatio * 0.5f
                            scaleX = scale
                            scaleY = scale
                        }
                        .size(profilePictureSize)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.onSurface,
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )
            }


        }
        if (state.isLogoutDialogVisible) {
            Dialog(onDismissRequest = {
                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
            }) {
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.surface,
                        shape = MaterialTheme.shapes.medium)
                        .padding(SpaceMedium)
                ) {
                    Text(text = stringResource(id = R.string.do_you_want_to_logout))
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = stringResource(id = R.string.no).uppercase(),
                            color = MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                            })
                        Spacer(modifier = Modifier.width(SpaceMedium))
                        Text(text = stringResource(id = R.string.yes).uppercase(),
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(ProfileEvent.Logout)
                                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                                onLogout()

                            })
                    }
                }

            }
        }

    }


}
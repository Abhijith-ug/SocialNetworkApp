package com.abhijith.socialnetworkapp.featurepost.presentation.mainFeed

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.presentation.components.Post
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.Screen
import com.abhijith.socialnetworkapp.featurepost.presentation.personlist.PostEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MainFeedScreen(
    onNavigate: (String) -> Unit = { },
    onNavigateUp : () -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: MainFeedViewModel = hiltViewModel()
) {

    val pagingState = viewModel.pagingState.value
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {
            event ->
            when(event){
                is PostEvent.onLiked -> {

                }
            }
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold
                )
            }, modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions =
        {
            IconButton(onClick = {
                onNavigate(Screen.SearchScreen.route)
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")

            }
        })
        Box(modifier = Modifier.fillMaxSize()){
            LazyColumn{
                items(pagingState.items.size){
                    i ->
                    val post  = pagingState.items[i]
                    if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                        viewModel.loadNextPost()
                    }
                    Post(
                        post = Post(
                            id = post.id  ,
                            userId = post.userId,
                            isLiked = post.isLiked,
                            username =post.username ,
                            imageUrl = Constants.BASE_URL+post.imageUrl ,
                            profilePictureUrl = Constants.BASE_URL+ post.profilePictureUrl ,
                            description = post.description,
                            likeCount = post.likeCount ,
                            commentCount = post.commentCount
                        ),
                        onUsernameClick = {
                            onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}")

                        },
                        onPostClick = {
                            onNavigate(Screen.PostDetailScreen.route + "/${post.id}")
                        },
                        onCommentClick = {
                            onNavigate(Screen.PostDetailScreen.route + "/${post.id}?shouldShowKeyboard=true")

                        },
                        onLikeClick = {
                              viewModel.onEvent(MainFeedEvents.LikedPost(post?.id ?:""))
                        }

                    )
                    if(i<pagingState.items.size-1){
                        Spacer(modifier = Modifier.height(SpaceLarge))
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(90.dp))
                }

            }
            if (pagingState.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
        }


    }
}
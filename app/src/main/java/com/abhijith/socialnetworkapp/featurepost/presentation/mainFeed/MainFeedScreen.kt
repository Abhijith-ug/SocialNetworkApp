package com.abhijith.socialnetworkapp.featurepost.presentation.mainFeed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.presentation.components.Post
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.util.Screen
import kotlinx.coroutines.launch

@Composable
fun MainFeedScreen(
    onNavigate: (String) -> Unit = { },
    onNavigateUp : () -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: MainFeedViewModel = hiltViewModel()
) {
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
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
            if (state.isLoadingFirstTime){
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
            LazyColumn{
                items(posts){
                        post ->
                    Post(
                        post = Post(
                            id = post?.id ?:"" ,
                            userId = post?.userId?:"",
                            isLiked = post?.isLiked?:false,
                            username =post?.username ?: "" ,
                            imageUrl = post?.imageUrl ?: "",
                            profilePictureUrl = post?.profilePictureUrl ?: "",
                            description = post?.description ?: "",
                            likeCount = post?.likeCount ?: 0,
                            commentCount = post?.commentCount ?: 0
                        ), onPostClick = {
                            onNavigate(Screen.PostDetailScreen.route + "/${post?.id}")
                        },

                    )
                }
                item {
                    if (state.isLoadingNewPosts){
                        CircularProgressIndicator(modifier = Modifier.align(BottomCenter))
                    }
                }
                posts.apply {
                    when{
                        loadState.refresh !is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvents.LoadedPage)
                        }
                        loadState.append is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvents.LoadMorePosts)
                        }
                        loadState.append is LoadState.NotLoading -> {
                            viewModel.onEvent(MainFeedEvents.LoadedPage)
                        }
                        loadState.append is LoadState.Error -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Error"
                                )
                            }

                        }
                    }
                }
            }
        }


    }
}
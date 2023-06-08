package com.abhijith.socialnetworkapp.featureprofile.presentation.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.User
import com.abhijith.socialnetworkapp.core.presentation.components.StandardTextField
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.components.UserProfileItem
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.IconSizeMedium
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.core.util.Screen
import kotlin.math.log

@Composable
fun SearchScreen(
    onNavigate: (String) -> Unit = { },
    onNavigateUp: () -> Unit = {},
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.searchState.value
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            StandardToolbar(
                onNavigateUp = onNavigateUp,
                showBackArrow = true
            ) {
                Text(
                    text = stringResource(id = R.string.search_for_users),
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colors.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceLarge)
            ) {

                StandardTextField(modifier = Modifier.fillMaxWidth(),
                    text = viewModel.searchFieldState.value.text,
                    hint = stringResource(id = R.string.search),
                    error = "",
                    leadingIcon = Icons.Default.Search,
                    onValueChange = {
                        viewModel.onEvent(event = SearchEvent.Query(it))
                    }
                )
                Spacer(modifier = Modifier.height(SpaceLarge))

                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    items(state.userItems) { user ->
                        Log.d("UserID",user.userId)
                        UserProfileItem(
                            user = User(
                                userId = user.userId,
                                profilePictureUrl = user.profilePictureUrl,
                                username = user.username,
                                description = user.bio,
                                followerCount = 0,
                                followingCount = 0,
                                postCount = 0
                            ),
                            actionIcon = {

                                    IconButton(onClick = {
                                                         viewModel.onEvent(SearchEvent.ToggleFollowState(user.userId))
                                    }, modifier = Modifier.size(
                                        IconSizeMedium)) {
                                        Icon(
                                            imageVector = if (user.isFollowing){
                                                                               Icons.Default.PersonRemove
                                                                               }else{
                                                                                    Icons.Default.PersonAdd
                                                                                    },
                                            contentDescription = null,
                                            tint = MaterialTheme.colors.onBackground
                                        )
                                    }
                            },
                            onItemClick = { onNavigate(Screen.ProfileScreen.route + "?userId=${user.userId}") }
                        )
                        Spacer(modifier = Modifier.height(SpaceMedium))
                    }
                }

            }

        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}
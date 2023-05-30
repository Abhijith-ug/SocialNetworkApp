package com.abhijith.socialnetworkapp.featurepost.presentation.MainFeed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.presentation.components.Post
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.util.Screen

@Composable
fun MainFeedScreen(
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        StandardToolbar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold
                )
            }, modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            navActions = {
                IconButton(onClick = {
                    navController.navigate(Screen.SearchScreen.route)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")

                }
            }
        )
        Post(
            post = Post(
                username = "Abhijith",
                imageUrl = "",
                profilePictureUrl = "",
                description = "kdfkdng igidnfn sonfgsngso n sdingds fsg sdgning  sfna goangagdkng dbgjdbgd  sbgs gsj",
                likeCount = 17,
                commentCount = 7
            ), onPostClick = {
                navController.navigate(Screen.PostDetailScreen.route)
            }
        )
    }
}
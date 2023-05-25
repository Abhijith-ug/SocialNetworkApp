package com.abhijith.socialnetworkapp.presentation.MainFeed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.domain.models.Post
import com.abhijith.socialnetworkapp.presentation.components.Post
import com.abhijith.socialnetworkapp.presentation.components.StandardScaffold
import com.abhijith.socialnetworkapp.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.presentation.util.Screen

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
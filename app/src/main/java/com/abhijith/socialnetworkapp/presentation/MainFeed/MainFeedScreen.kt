package com.abhijith.socialnetworkapp.presentation.MainFeed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.domain.models.Post
import com.abhijith.socialnetworkapp.presentation.components.Post
import com.abhijith.socialnetworkapp.presentation.components.StandardScaffold

@Composable
fun MainFeedScreen(
    navController: NavController
){
    StandardScaffold(navController=navController, modifier = Modifier.fillMaxSize()) {
        Post(post = Post(
            username = "Abhijith",
            imageUrl = "",
            profilePictureUrl = "",
            description = "kdfkdng igidnfn sonfgsngso n sdingds fsg sdgning  sfna goangagdkng dbgjdbgd  sbgs gsj",
            likeCount = 17,
            commentCount = 7
        ))
    }


}
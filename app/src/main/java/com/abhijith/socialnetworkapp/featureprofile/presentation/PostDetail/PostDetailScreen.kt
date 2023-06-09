package com.abhijith.socialnetworkapp.featureprofile.presentation.PostDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.Comment
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.presentation.components.ActionRow
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.*

@Composable
fun PostDetailScreen(
    onNavigate: (String) -> Unit = { },
    onNavigateUp : () -> Unit = {},    post: Post,
) {
    Column(modifier = Modifier.fillMaxWidth()) {

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
                Image(
                    painterResource(id = R.drawable.img),
                    contentDescription = "post image",
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpaceLarge)
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
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    items(20){
                        Comment(
                            modifier = Modifier.fillMaxWidth( )
                                .padding(horizontal = SpaceLarge,
                                vertical = SpaceSmall
                                ),
                            comment = Comment(
                                username = "Abhijith",
                                comment = "kdfkdng igidnfn sonfgsngso n sdingds fsg sdgning  sfna goangagdkng dbgjdbgd  sbgs gsj",
                            )
                        )
                    }
                }



            }
            Image(
                painterResource(id = R.drawable.profilepic),
                contentDescription = "profilePicture",
                modifier = Modifier
                    .size(profilePictureSizeMedium)
                    .clip(CircleShape)
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.Crop
            )

        }


    }

}

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment = Comment(),
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
                        painter = painterResource(id = R.drawable.profilepic),
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
                    text = "2 days ago",
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

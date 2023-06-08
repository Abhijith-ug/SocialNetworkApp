package com.abhijith.socialnetworkapp.featureprofile.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceSmall
import com.abhijith.socialnetworkapp.core.util.Constants
import com.abhijith.socialnetworkapp.core.util.toPx
import com.abhijith.socialnetworkapp.featureprofile.domain.model.Skill

@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    iconSize: Dp = 35.dp,
    leftIconModifier: Modifier = Modifier,
    rightIconModifier: Modifier = Modifier,
    bannerUrl: String? = null,
    topSkills:List<Skill> = emptyList(),
    shouldShowGitHub:Boolean = false,
    shouldShowInstagram:Boolean = false,
    shouldShowLinkedIn:Boolean = false,
    onIconGroupWidthChange: (Int) -> Unit = {},
    onGitHubClick: () -> Unit = {},
    onInstagramClick: () -> Unit = {},
    onLinkedInClick: () -> Unit = {}
) {
    BoxWithConstraints(modifier = modifier) {
        Image(
            painter = rememberImagePainter(data = bannerUrl,
                builder = {
                    crossfade(true)
                }), contentDescription =
            stringResource(id = R.string.banner_image),
            modifier = modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth

        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = constraints.maxHeight - iconSize.toPx() * 2f
                    )
                )
        )
        Row(
            modifier = Modifier
                .height(iconSize)
                .align(Alignment.BottomStart)
                .padding(SpaceSmall)
        ) {

            topSkills.forEach { skill ->
                Spacer(modifier = Modifier.width(SpaceSmall))
                Image(
                    painter = rememberImagePainter(data = "${Constants.BASE_URL}${skill.imageUrl}",
                        builder = {
                            crossfade(true)
                        }),
                    contentDescription = null,
                    modifier = Modifier.height(iconSize)
                )
            }
        }
        Row(
            modifier = Modifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(SpaceSmall)
        ) {

            if (shouldShowGitHub) {
                IconButton(
                    onClick = { onGitHubClick },
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_github),
                        contentDescription = "github",
                        modifier = Modifier.size(iconSize)

                    )

                }
            }

            if (shouldShowInstagram) {
                IconButton(
                    onClick = { onInstagramClick },
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_instagram),
                        contentDescription = "instagram",
                        modifier = Modifier.size(iconSize)

                    )

                }
            }

            if (shouldShowLinkedIn) {
                IconButton(
                    onClick = { onLinkedInClick },
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_linkedl),
                        contentDescription = "linkedln",
                        modifier = Modifier.size(iconSize)
                    )

                }


            }

        }


    }
}
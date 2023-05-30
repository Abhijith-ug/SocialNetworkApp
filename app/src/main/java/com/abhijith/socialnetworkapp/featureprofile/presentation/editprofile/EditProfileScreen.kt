package com.abhijith.socialnetworkapp.featureprofile.presentation.editprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.presentation.components.StandardTextField
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.IconSizeLarge
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.profilePictureSizeLarge
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.featureprofile.presentation.util.EditProfileError
import com.google.accompanist.flowlayout.MainAxisAlignment

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    profilePictureSize: Dp = profilePictureSizeLarge,
    viewModel  : EditProfileViewModel = hiltViewModel()
) {

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolbar(navController = navController,
            showBackArrow = true, title = {
                Text(text = stringResource(id = R.string.edit_your_profile),
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colors.onBackground
                )
            })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
           BannerEditSection(bannerImage = painterResource(id = R.drawable.channelart),
               profileImage = painterResource(id = R.drawable.profilepic ),profilePictureSize)
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceLarge)) {

                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(modifier = Modifier.fillMaxWidth(),
                    text = viewModel.usernameState.value.text,
                    hint = stringResource(id = R.string.username),
                    error =   when(viewModel.usernameState.value.error){
                                                                       is EditProfileError.FieldEmpty -> stringResource(
                                                                           id = R.string.this_field_cant_be_empty
                                                                       )
                        else -> ""
                                                                       },
                    leadingIcon = Icons.Default.Person,
                    onValueChange = {viewModel.setUsernameState(
                        StandardTextFieldState(text = it)
                    )}
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(modifier = Modifier.fillMaxWidth(),
                    text = viewModel.githubTextFieldState.value.text,
                    hint = stringResource(id = R.string.github_url),
                    error =   when(viewModel.githubTextFieldState.value.error){
                        is EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.this_field_cant_be_empty
                        )
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_github),
                    onValueChange = {viewModel.setGithubTextFieldState(
                        StandardTextFieldState(text = it)
                    )}
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(modifier = Modifier.fillMaxWidth(),
                    text = viewModel.instagramTextFieldState.value.text,
                    hint = stringResource(id = R.string.instagram_url),
                    error =   when(viewModel.instagramTextFieldState.value.error){
                        is EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.this_field_cant_be_empty
                        )
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_instagram),
                    onValueChange = {viewModel.setInstagramTextFieldState(
                        StandardTextFieldState(text = it)
                    )}
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(modifier = Modifier.fillMaxWidth(),
                    text = viewModel.linkedInTextFieldState.value.text,
                    hint = stringResource(id = R.string.linkeIn_url),
                    error =   when(viewModel.linkedInTextFieldState.value.error){
                        is EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.this_field_cant_be_empty
                        )
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_linkedl),
                    onValueChange = {viewModel.setLinkedInTextFieldState(
                        StandardTextFieldState(text = it)
                    )}
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                StandardTextField(modifier = Modifier.fillMaxWidth(),
                    text = viewModel.bioFieldState.value.text,
                    hint = stringResource(id = R.string.your_bio),
                    error =   when(viewModel.linkedInTextFieldState.value.error){
                        is EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.this_field_cant_be_empty
                        )
                        else -> ""
                    },
                    leadingIcon = Icons.Default.Description,
                    singleLine = false,
                    onValueChange = {viewModel.setbioTextFieldState(
                        StandardTextFieldState(text = it)
                    )}
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Text(text = stringResource(id = R.string.select_top_3_skills),
                style = MaterialTheme.typography.h2, textAlign = TextAlign.Center,
                modifier = Modifier.align(CenterHorizontally))

                Spacer(modifier = Modifier.height(SpaceMedium))
                FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                    listOf(
                        "Kotlin",
                        "JavaScript" ,
                       "Kotlin",
                        "Assembly",
                        "C++",
                        "Dart",
                        "C#",
                        "TypeScript",
                        "Python"
                    ).forEach {

                          Chip(onClick = { /*TODO*/ }, ) {

                          }



                        }


                    }

                }

            }



        }

    }


@Composable
fun BannerEditSection(
    bannerImage:Painter,
    profileImage:Painter,
    profilePictureSize: Dp = profilePictureSizeLarge,
    onBannerClick:() -> Unit = {},
    onProfileImageClick:() -> Unit ={}

){
    val bannerHeight = (LocalConfiguration.current.screenWidthDp /2.5f).dp


    Box(modifier = Modifier
        .fillMaxWidth()
        .height(bannerHeight + profilePictureSize / 2f)) {

        Image(
            painter = bannerImage, contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(bannerHeight)
        )
        Image(
            painter = profileImage, contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
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
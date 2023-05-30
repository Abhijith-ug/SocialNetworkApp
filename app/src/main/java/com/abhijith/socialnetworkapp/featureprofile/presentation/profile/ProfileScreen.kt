package com.abhijith.socialnetworkapp.featureprofile.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.domain.models.User
import com.abhijith.socialnetworkapp.core.presentation.components.Post
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.featureprofile.presentation.profile.components.BannerSection
import com.abhijith.socialnetworkapp.featureprofile.presentation.profile.components.ProfileHeaderSection
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.profilePictureSizeLarge
import com.abhijith.socialnetworkapp.core.util.Screen

@Composable
fun ProfileScreen(navController: NavController){


    Column(modifier = Modifier.fillMaxWidth()) {

        StandardToolbar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_profile),
                    fontWeight = FontWeight.Bold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = -profilePictureSizeLarge / 2f),


            ){
            item {
                BannerSection(
                    modifier = Modifier.aspectRatio(2.5f)
                )
            }


            item {
                ProfileHeaderSection(user =
                User(
                    profilePictureUrl = "",
                    username = "Abhijith U G",
                    description = "dshgjdhg shjh diheh gdjhd glsdg sdighd gow gidhdh nshgdhg" +
                            "igdihgdi ghdighd gsihg sidgjd gsohg  sd  gdi gdh idhd gi dddddi ",
                    followerCount = 254,
                    followingCount = 204,
                    postCount = 23
                ), onEditClick = { navController.navigate(Screen.EditProfileScreen.route)}
                )
            }
            items(20){
                Spacer(modifier =Modifier.height(SpaceMedium)
                    .offset(y = -profilePictureSizeLarge / 2f))
                Post(
                    post = Post(
                        username = "Abhijith",
                        imageUrl = "",
                        profilePictureUrl = "",
                        description = "kdfkdng igidnfn sonfgsngso n sdingds fsg sdgning  sfna goangagdkng dbgjdbgd  sbgs gsj",
                        likeCount = 17,
                        commentCount = 7
                    ), showProfileImage = false, onPostClick = {
                        navController.navigate(Screen.PostDetailScreen.route)
                    },
                    modifier = Modifier.
                            offset(y = - profilePictureSizeLarge / 2f)

                )

            }

        }






    }




}
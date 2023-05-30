package com.abhijith.socialnetworkapp.featureprofile.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.User
import com.abhijith.socialnetworkapp.core.presentation.components.StandardTextField
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.components.UserProfileItem
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.IconSizeMedium
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.featureprofile.presentation.util.EditProfileError

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
){
    Column(modifier = Modifier.fillMaxSize()) {

        StandardToolbar(navController = navController,
        showBackArrow = true,
        title = {
            Text(text = stringResource(id = R.string.search_for_users),
            fontWeight = FontWeight.Bold,color = MaterialTheme.colors.onBackground)
        })
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(SpaceLarge)) {

            StandardTextField(modifier = Modifier.fillMaxWidth(),
                text = viewModel.searchState.value.text,
                hint = stringResource(id = R.string.search),
                error = "",
                leadingIcon = Icons.Default.Search,
                onValueChange = {viewModel.setSearchState(
                    StandardTextFieldState(text = it)
                )}
            )
            Spacer(modifier = Modifier.height(SpaceLarge))
            
            LazyColumn(modifier = Modifier.fillMaxSize()){

                items(10){
                    UserProfileItem(user = User(
                        profilePictureUrl = "",
                        username = "Abhijith U G",
                        description = "dshgjdhg shjh diheh gdjhd glsdg sdighd gow gidhdh nshgdhg" +
                                "igdihgdi ghdighd gsihg sidgjd gsohg  sd  gdi gdh idhd gi dddddi ",
                        followerCount = 254,
                        followingCount = 204,
                        postCount = 23
                    ),
                        actionIcon = {
                         Icon(imageVector = Icons.Default.PersonAdd, contentDescription = null,
                         modifier = Modifier.size(IconSizeMedium))
                        }
                    )
                }
            }

        }
        
    }
}
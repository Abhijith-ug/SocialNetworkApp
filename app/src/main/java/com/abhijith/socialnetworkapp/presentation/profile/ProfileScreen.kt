package com.abhijith.socialnetworkapp.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.domain.models.Activity
import com.abhijith.socialnetworkapp.domain.util.ActivityAction
import com.abhijith.socialnetworkapp.domain.util.DateFormatUtil
import com.abhijith.socialnetworkapp.presentation.activity.ActivityItem
import com.abhijith.socialnetworkapp.presentation.components.StandardScaffold
import com.abhijith.socialnetworkapp.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.presentation.ui.theme.SpaceMedium
import kotlin.random.Random

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
                .background(MaterialTheme.colors.surface),
            contentPadding = PaddingValues(SpaceMedium)
        ){
            items(20){


            }

        }






    }




}
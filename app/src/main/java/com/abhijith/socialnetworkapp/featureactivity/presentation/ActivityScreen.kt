package com.abhijith.socialnetworkapp.featureactivity.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.Activity
import com.abhijith.socialnetworkapp.domain.util.ActivityAction
import com.abhijith.socialnetworkapp.domain.util.DateFormatUtil
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.presentation.activity.ActivityItem
import kotlin.random.Random

@Composable
fun ActivityScreen(navController: NavController ,
viewModel: ActivityViewModel = hiltViewModel()){
    Column(modifier = Modifier.fillMaxWidth()) {

        StandardToolbar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_activity),
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

                ActivityItem(activity =
                Activity(
                    "Abhijith",
                    actionType = if (Random.nextInt(2)==0){
                        ActivityAction.LikedPost
                    }else ActivityAction.CommentedOnPost,
                    formattedTime = DateFormatUtil.timestampToFormattedString(
                        timestamp = System.currentTimeMillis(),
                        pattern = "MMM dd, HH:mm"
                    )
                )
                )
                if (it<19){
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }

            }

        }






    }

}
package com.abhijith.socialnetworkapp.featureactivity.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.test.core.app.ActivityScenario
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.models.Activity
import com.abhijith.socialnetworkapp.domain.util.DateFormatUtil
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.domain.util.ActivityType
import com.abhijith.socialnetworkapp.presentation.activity.ActivityItem
import kotlin.random.Random

@Composable
fun ActivityScreen(onNavigate: (String) -> Unit = { },
                   onNavigateUp : () -> Unit = {},
viewModel: ActivityViewModel = hiltViewModel()){

    var state = viewModel.state.value
    val activities = viewModel.activities.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxWidth()) {

            StandardToolbar(
                onNavigateUp = onNavigateUp,
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
                items(activities){
                    activity ->
                    activity?.let {
                        ActivityItem(activity =
                        Activity(activity.userId,
                            activityType = activity.activityType,
                            formattedTime = activity.formattedTime,
                            parentId = activity.parentId,
                            username = activity.username
                        ),
                            onNavigate = onNavigate
                        )

                    }




                }

            }






        }



    }

}
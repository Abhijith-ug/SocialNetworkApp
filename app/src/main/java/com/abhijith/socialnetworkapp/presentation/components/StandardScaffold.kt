package com.abhijith.socialnetworkapp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.outlined.Doorbell
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.domain.models.BottomNavItem
import com.abhijith.socialnetworkapp.presentation.ui.theme.HintGray
import com.abhijith.socialnetworkapp.presentation.util.Screen




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar:Boolean = true,
    viewModel: StandardScaffoldViewModel = hiltViewModel(),
    bottomNavItems: List<BottomNavItem> = listOf(    BottomNavItem(
        route = Screen.MainFeedScreen.route,
        icon = Icons.Outlined.Home,
        contentDescription = "Home"
    ),
        BottomNavItem(
            route = Screen.ChatScreen.route,
            icon = Icons.Outlined.Message,
            contentDescription = "Message"
        ),
        BottomNavItem(
            route = Screen.ActivityScreen.route,
            icon = Icons.Outlined.Doorbell,
            contentDescription = "Activity"
        ),
        BottomNavItem(
            route = Screen.ProfileScreen.route,
            icon = Icons.Outlined.Person,
            contentDescription = "Profile"
        )),
    content: @Composable () -> Unit
) {
    Scaffold(bottomBar = {
        if (showBottomBar){
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.surface,
                cutoutShape = CircleShape,
                elevation = 5.dp
            ) {
                BottomNavigation {
                    bottomNavItems.forEachIndexed { i, bottomNavItem ->
                        StandardBottomNavItem(
                            icon = bottomNavItem.icon,
                            contentDescription = bottomNavItem.contentDescription,
                            selected = bottomNavItem.route == navController.currentDestination?.route,
                            alertCount = bottomNavItem.alertCount
                        ) {
//                        viewModel.setSelectedBottomNavItem(i)
                            navController.navigate(bottomNavItem.route)
                        }
                    }

                }


            }
        }


    }, modifier = modifier) {

        content()

    }
}
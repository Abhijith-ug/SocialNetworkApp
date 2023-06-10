package com.abhijith.socialnetworkapp.core.presentation.components

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Screen
import com.abhijith.socialnetworkapp.featurepost.presentation.mainFeed.MainFeedScreen
import com.abhijith.socialnetworkapp.featurepost.presentation.PostDetail.PostDetailScreen
import com.abhijith.socialnetworkapp.featureactivity.presentation.ActivityScreen
import com.abhijith.socialnetworkapp.presentation.chat.ChatScreen
import com.abhijith.socialnetworkapp.featurepost.presentation.createpost.CreatePostScreen
import com.abhijith.socialnetworkapp.featureprofile.presentation.editprofile.EditProfileScreen
import com.abhijith.socialnetworkapp.featureauth.presentation.login.LoginScreen
import com.abhijith.socialnetworkapp.featureprofile.presentation.profile.ProfileScreen
import com.abhijith.socialnetworkapp.featureauth.presentation.register.RegisterScreen
import com.abhijith.socialnetworkapp.featureprofile.presentation.search.SearchScreen
import com.abhijith.socialnetworkapp.featureauth.presentation.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController, scaffoldState: ScaffoldState) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onPopBackStack = navController::popBackStack,
                onNavigate = navController::navigate
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, scaffoldState)
        }
        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.ChatScreen.route) {
            ChatScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
        composable(Screen.ActivityScreen.route) {
            ActivityScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate
            )
        }
        composable(route = Screen.ProfileScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            ProfileScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate, scaffoldState = scaffoldState,
                userId = it.arguments?.getString("userId"),
            )
        }
        composable(Screen.EditProfileScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                }
            )) {
            EditProfileScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate, scaffoldState = scaffoldState
            )
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate
            )
        }
        composable(Screen.CreatePostScreen.route) {
            CreatePostScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate, scaffoldState = scaffoldState
            )
        }
        composable(route = Screen.PostDetailScreen.route + "/{postId}",
        arguments = listOf(
            navArgument(
                name = "postId"
            ){
                type = NavType.StringType
            }
        )
        ) {
            PostDetailScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,

            )
        }

    }

}
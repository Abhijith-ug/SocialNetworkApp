package com.abhijith.socialnetworkapp.core.presentation.components

import android.content.Intent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.abhijith.socialnetworkapp.featurechat.presentation.message.MessageScreen
import com.abhijith.socialnetworkapp.featurepost.presentation.personlist.PersonListScreen

@OptIn(ExperimentalMaterialApi::class)
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
                onLogin = {
                    navController.popBackStack(
                        route = Screen.LoginScreen.route,
                        inclusive = true
                    )
                        navController.navigate(
                            route =  Screen.MainFeedScreen.route,
                        )
                },
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
        composable(Screen.MessageScreen.route) {
            MessageScreen(
                chatId = "",
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
                onLogout = {
                           navController.navigate(
                               route = Screen.LoginScreen.route,
                               ){
                               popUpTo(0)
                           }
                },
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
        composable(route = Screen.PostDetailScreen.route + "/{postId}?shouldShowKeyboard={shouldShowKeyboard}",
        arguments = listOf(
            navArgument(
                name = "postId"
            ){
                type = NavType.StringType
            },
            navArgument(
                name = "shouldShowKeyboard"
            ){
                type = NavType.BoolType
                defaultValue = false
            }
        ),
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = "https://abhijith.com/{postId}"
                }
            )
        ) {
            val shouldShowKeyboard = it.arguments?.getBoolean("shouldShowKeyboard") ?:false
            val postId = it.arguments?.getString("postId")
            PostDetailScreen(
                scaffoldState  = scaffoldState,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                shouldShowKeyboard = shouldShowKeyboard
            )
        }

        composable(route = Screen.PersonalListScreen.route + "/{parentId}",
        arguments = listOf(
            navArgument("parentId"){
                type = NavType.StringType
            }
        )
        ){
            PersonListScreen(
            onNavigateUp = navController::navigateUp,
            onNavigate = navController::navigate,
            scaffoldState = scaffoldState)
        }

    }

}
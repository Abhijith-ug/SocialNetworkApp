package com.abhijith.socialnetworkapp.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhijith.socialnetworkapp.presentation.MainFeed.MainFeedScreen
import com.abhijith.socialnetworkapp.presentation.activity.ActivityScreen
import com.abhijith.socialnetworkapp.presentation.chat.ChatScreen
import com.abhijith.socialnetworkapp.presentation.login.LoginScreen
import com.abhijith.socialnetworkapp.presentation.profile.ProfileScreen
import com.abhijith.socialnetworkapp.presentation.register.RegisterScreen
import com.abhijith.socialnetworkapp.presentation.splash.SplashScreen


@Composable
fun Navigation(navController:NavHostController) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
        composable(Screen.MainFeedScreen.route){
            MainFeedScreen(navController = navController)
        }
        composable(Screen.ChatScreen.route){
            ChatScreen(navController = navController)
        }
        composable(Screen.ActivityScreen.route){
            ActivityScreen(navController = navController)
        }
        composable(Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }

    }

}
package com.abhijith.socialnetworkapp.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = Screen.SplashScreen.route ){

    }

}
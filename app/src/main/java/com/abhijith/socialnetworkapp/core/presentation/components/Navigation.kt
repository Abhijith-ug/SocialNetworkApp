package com.abhijith.socialnetworkapp.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhijith.socialnetworkapp.core.domain.models.Post
import com.abhijith.socialnetworkapp.core.util.Screen
import com.abhijith.socialnetworkapp.featurepost.presentation.MainFeed.MainFeedScreen
import com.abhijith.socialnetworkapp.featureprofile.presentation.PostDetail.PostDetailScreen
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
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(navController = navController)
        }
        composable(Screen.ChatScreen.route) {
            ChatScreen(navController = navController)
        }
        composable(Screen.ActivityScreen.route) {
            ActivityScreen(navController = navController)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.EditProfileScreen.route) {
            EditProfileScreen(navController = navController)
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(navController = navController)
        }
        composable(Screen.CreatePostScreen.route) {
            CreatePostScreen(navController = navController)
        }
        composable(Screen.PostDetailScreen.route) {
            PostDetailScreen(
                navController = navController,
                post = Post(
                    username = "Abhijith",
                    imageUrl = "",
                    profilePictureUrl = "",
                    description = "kdfkdng igidnfn sonfgsngso n sdingds fsg sdgning  sfn" +
                            "sjdhsdjgsjhkjdshgdjshgdjshgkjshgsjhgjkhgjhd" +
                            "fjdshgsajgasgjajgajhgjhfdsjashgjsajgkjasngjkan dgjdsjgsaj gsdjgsgh oweognd gdn" +
                            "jgsajgsajgklsajgklsadgksajgkalsjg dkjgldsjglsdjglsdkj digdijdkjgkdjgjg" +
                            "kjgkdjkldjkdsnsgsj",
                    likeCount = 17,
                    commentCount = 7
                )
            )
        }

    }

}
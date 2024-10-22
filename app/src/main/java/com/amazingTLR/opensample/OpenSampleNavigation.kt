package com.amazingTLR.opensample

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amazingTLR.opensample.userList.UserListScreen
import com.amazingTLR.opensample.userprofile.UserProfileScreen

@Composable
fun OpenSampleNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.userListScreen, builder = {
        composable(Routes.userListScreen) {
            UserListScreen(navController = navController)
        }

        composable(
            route = "${Routes.userProfileScreen}/{$USER_LOGIN_ARG}",
            arguments = listOf(navArgument(USER_LOGIN_ARG) {
                type = NavType.StringType
            })
        ) {
            UserProfileScreen()
        }
    })
}

object Routes {
    var userListScreen = "userListScreen"
    var userProfileScreen = "userProfileScreen"
}

const val USER_LOGIN_ARG = "userLogin"
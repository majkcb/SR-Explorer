package com.example.sr_kodtest.screens.navigation

import androidx.navigation.NavController

class NavigationActions(navController: NavController) {
    val navigateToDetailScreen: (Int) -> Unit = { programId ->
        navController.navigate("programDetailScreen/$programId") {
            popUpTo(NavigationDestination.ProgramScreen.route)
        }
    }
}

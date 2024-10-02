package com.example.sr_kodtest.ui.theme.navigation

import androidx.navigation.NavController

class NavigationActions(navController: NavController) {
    val navigateToDetailScreen: (Int) -> Unit = { programId ->
        navController.navigate("programDetailScreen/$programId") {
            popUpTo(NavigationDestination.ProgramScreen.route)
        }
    }
}

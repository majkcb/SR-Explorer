package com.example.sr_kodtest.navigation

sealed class NavigationDestination(val route: String) {
    data object ProgramScreen : NavigationDestination("programScreen")
    data object ProgramDetailScreen : NavigationDestination("programDetailScreen")
}
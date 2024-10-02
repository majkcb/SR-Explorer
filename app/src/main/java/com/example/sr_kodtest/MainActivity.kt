package com.example.sr_kodtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sr_kodtest.ui.theme.SRKodtestTheme
import com.example.sr_kodtest.ui.theme.navigation.NavigationActions
import com.example.sr_kodtest.ui.theme.navigation.NavigationDestination
import com.example.sr_kodtest.ui.theme.screens.ProgramDetailScreen
import com.example.sr_kodtest.ui.theme.screens.ProgramScreen
import com.example.sr_kodtest.ui.theme.screens.ProgramViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SRKodtestTheme {

                val navController = rememberNavController()
                val navActions = remember(navController) {
                    NavigationActions(navController)
                }

                var shouldShowBackButton by remember { mutableStateOf(false) }
                var topBarTitle by remember { mutableStateOf(getString(R.string.title)) }

                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            topBarTitle, modifier = Modifier.padding(top = 16.dp)
                        )
                    }, navigationIcon = {
                        if (shouldShowBackButton) {
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.backward_arrow),
                                    tint = Color.Black
                                )
                            }
                        }
                    })
                }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavigationDestination.ProgramScreen.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(NavigationDestination.ProgramScreen.route) {
                            shouldShowBackButton = false
                            topBarTitle = getString(R.string.title)
                            ProgramScreen(programViewModel = hiltViewModel(),
                                navigateToDetailScreen = { programId ->
                                    navActions.navigateToDetailScreen(programId)
                                })

                        }
                        composable("programDetailScreen/{programId}") { backStackEntry ->
                            val programId =
                                backStackEntry.arguments?.getString("programId")?.toInt()
                            programId?.let {
                                shouldShowBackButton = true

                                topBarTitle = getString(R.string.title)

                                ProgramDetailScreen(
                                    programViewModel = hiltViewModel(), programId = it
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

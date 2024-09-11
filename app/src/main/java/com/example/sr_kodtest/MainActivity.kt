package com.example.sr_kodtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sr_kodtest.navigation.NavigationActions
import com.example.sr_kodtest.navigation.NavigationDestination
import com.example.sr_kodtest.program.ProgramDetailScreen
import com.example.sr_kodtest.program.ProgramScreen
import com.example.sr_kodtest.ui.theme.SRKodtestTheme
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

                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            stringResource(R.string.title),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    })
                }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavigationDestination.ProgramScreen.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(NavigationDestination.ProgramScreen.route) {
                            ProgramScreen(programViewModel = hiltViewModel(),
                                navigateToDetailScreen = { programId ->
                                    navActions.navigateToDetailScreen(programId)
                                })

                        }
                        composable("programDetailScreen/{programId}") { backStackEntry ->
                            val programId =
                                backStackEntry.arguments?.getString("programId")?.toInt()
                            programId?.let {
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

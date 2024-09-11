package com.example.sr_kodtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sr_kodtest.program.ProgramScreen
import com.example.sr_kodtest.ui.theme.SRKodtestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SRKodtestTheme {
                ProgramScreen(programViewModel = hiltViewModel())
            }
        }
    }
}

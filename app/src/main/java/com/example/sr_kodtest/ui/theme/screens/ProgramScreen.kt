package com.example.sr_kodtest.ui.theme.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sr_kodtest.R
import com.example.sr_kodtest.domain.models.Program
import com.example.sr_kodtest.ui.theme.SRKodtestTheme
import com.example.sr_kodtest.ui.theme.composables.ListOfPrograms

@Composable
fun ProgramScreen(
    modifier: Modifier = Modifier,
    programViewModel: ProgramViewModel,
    navigateToDetailScreen: (Int) -> Unit
) {
    val state by programViewModel.uiState.collectAsState()

    val likedPrograms = state.programs.filter { program ->
        state.favoritePrograms.any { it.programName == program.name }
    }
    val notLikedPrograms = state.programs.filterNot { program ->
        state.favoritePrograms.any { it.programName == program.name }
    }

    ProgramScreenContent(modifier = modifier,
        isLoading = state.isLoading,
        errorMessage = state.errorMessage,
        likedPrograms = likedPrograms,
        notLikedPrograms = notLikedPrograms,
        selectProgram = { program ->
            program.id?.let { programId ->
                navigateToDetailScreen(programId)
            }
        })
}

@Composable
fun ProgramScreenContent(
    modifier: Modifier,
    isLoading: Boolean,
    errorMessage: Int?,
    likedPrograms: List<Program>,
    notLikedPrograms: List<Program>,
    selectProgram: (Program) -> Unit
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.program_title),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 32.sp,
            letterSpacing = (-0.4).sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            errorMessage != null -> {
                Text(
                    text = stringResource(errorMessage),
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            else -> {
                ListOfPrograms(
                    likedPrograms = likedPrograms,
                    notLikedPrograms = notLikedPrograms,
                    selectProgram = selectProgram
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProgramScreenPreview() {
    SRKodtestTheme {
        val previewProgramLiked = Program(
            id = 1,
            name = "Liked Program",
            description = "Description of liked program",
            programimage = "https://via.placeholder.com/150",
            broadcastinfo = "Broadcast info for liked program"
        )

        val previewProgramNotLiked = Program(
            id = 2,
            name = "Not Liked Program",
            description = "Description of not liked program",
            programimage = "https://via.placeholder.com/150",
            broadcastinfo = "Broadcast info for not liked program"
        )

        ProgramScreenContent(modifier = Modifier,
            isLoading = false,
            errorMessage = null,
            likedPrograms = listOf(previewProgramLiked),
            notLikedPrograms = listOf(previewProgramNotLiked),
            selectProgram = {})
    }
}

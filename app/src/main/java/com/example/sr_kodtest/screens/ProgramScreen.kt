package com.example.sr_kodtest.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sr_kodtest.R
import com.example.sr_kodtest.models.Program
import com.example.sr_kodtest.ui.theme.SRKodtestTheme

@Composable
fun ProgramScreen(
    modifier: Modifier = Modifier,
    programViewModel: ProgramViewModel,
    favoriteProgramViewModel: FavoriteProgramViewModel,
    navigateToDetailScreen: (Int) -> Unit
) {
    val state by programViewModel.uiState.collectAsState()
    val favoritePrograms by favoriteProgramViewModel.favoritePrograms.collectAsState()

    // Separera gillade och ogillade program
    val likedPrograms = state.programs.filter { program ->
        favoritePrograms.any { it.programName== program.name }
    }
    val notLikedPrograms = state.programs.filterNot { program ->
        favoritePrograms.any { it.programName == program.name }
    }

    ProgramScreen(
        modifier = modifier,
        isLoading = state.isLoading,
        likedPrograms = likedPrograms,
        notLikedPrograms = notLikedPrograms,
        selectProgram = { program ->
            program.id?.let { programId ->
                navigateToDetailScreen(programId)
            }
        }
    )
}

@Composable
fun ProgramScreen(
    modifier: Modifier,
    isLoading: Boolean,
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

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                // Visa gillade program
                item {
                    Text(text ="Liked", style = MaterialTheme.typography.bodyLarge)
                }
                items(likedPrograms) { program ->
                    ProgramItem(
                        programImage = program.programimage,
                        name = program.name,
                        broadCastInfo = program.broadcastinfo ?: "",
                        selectProgram = { selectProgram(program) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Visa ogillade program
                item {
                    Text(text = "Not liked", style = MaterialTheme.typography.bodyLarge)
                }
                items(notLikedPrograms) { program ->
                    ProgramItem(
                        programImage = program.programimage,
                        name = program.name,
                        broadCastInfo = program.broadcastinfo ?: "",
                        selectProgram = { selectProgram(program) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
fun ProgramItem(
    programImage: String, name: String, broadCastInfo: String, selectProgram: () -> Unit
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { selectProgram() })
    {

        AsyncImage(
            model = programImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(RectangleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = broadCastInfo,
                fontSize = 14.sp,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
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

        ProgramScreen(
            modifier = Modifier,
            isLoading = false,
            likedPrograms = listOf(previewProgramLiked),
            notLikedPrograms = listOf(previewProgramNotLiked),
            selectProgram = {}
        )
    }
}

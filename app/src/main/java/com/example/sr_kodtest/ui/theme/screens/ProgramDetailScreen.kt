package com.example.sr_kodtest.ui.theme.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sr_kodtest.R
import com.example.sr_kodtest.ui.theme.SRKodtestTheme

@Composable
fun ProgramDetailScreen(
    programId: Int, programViewModel: ProgramViewModel
) {
    val program = programViewModel.getProgramById(programId)

    if (program != null) {
        val state by programViewModel.uiState.collectAsState()
        val isFavorite = state.favoritePrograms.any { fav -> fav.programId == program.id }

        ProgramDetailContent(programImage = program.programimage,
            name = program.name,
            description = program.description,
            isFavorite = isFavorite,
            onFavoriteToggle = { newIsFavorite ->
                if (newIsFavorite) {
                    program.id?.let { programViewModel.addFavoriteProgram(it) }
                } else {
                    program.id?.let { programViewModel.deleteFavoriteProgram(it) }
                }
            })
    } else {
        Text(stringResource(R.string.program_not_found))
    }
}


@Composable
fun ProgramDetailContent(
    programImage: String,
    name: String,
    description: String,
    isFavorite: Boolean,
    onFavoriteToggle: (Boolean) -> Unit
) {
    var showToast by remember { mutableStateOf(false) }
    var isFav by remember { mutableStateOf(isFavorite) }

    if (showToast) {
        val message = if (isFav) {
            stringResource(R.string.added_to_favorites)
        } else {
            stringResource(R.string.removed_from_favorites)
        }
        Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
        showToast = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = programImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp)
                .clip(RectangleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
            IconButton(onClick = {
                isFav = !isFav
                onFavoriteToggle(isFav)
                showToast = true
            }) {
                Icon(
                    imageVector = if (isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFav) stringResource(R.string.desc_remove_from_favorites) else stringResource(
                        R.string.desc_add_to_favorites
                    ),
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = description,
            fontSize = 16.sp,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProgramDetailScreenPreview() {
    SRKodtestTheme {
        ProgramDetailContent(
            programImage = "Img",
            name = "P3",
            description = "Desc",
            onFavoriteToggle = {},
            isFavorite = true
        )
    }
}




package com.example.sr_kodtest.ui.theme.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sr_kodtest.R
import com.example.sr_kodtest.domain.models.Program

@Composable
fun ListOfPrograms(
    likedPrograms: List<Program>, notLikedPrograms: List<Program>, selectProgram: (Program) -> Unit
) {
    LazyColumn {
        item {
            Text(
                text = stringResource(R.string.liked), style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (likedPrograms.isEmpty()) {
            item {
                Text(
                    text = stringResource(id = R.string.no_programs_found),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            items(likedPrograms) { program ->
                ProgramItem(programImage = program.programimage,
                    name = program.name,
                    broadCastInfo = program.broadcastinfo ?: "",
                    selectProgram = { selectProgram(program) })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            Text(
                text = stringResource(R.string.not_liked),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        items(notLikedPrograms) { program ->
            ProgramItem(programImage = program.programimage,
                name = program.name,
                broadCastInfo = program.broadcastinfo ?: "",
                selectProgram = { selectProgram(program) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
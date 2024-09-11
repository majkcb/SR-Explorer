package com.example.sr_kodtest.program

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProgramDetailScreen(programViewModel: ProgramViewModel, programId: Int) {

    val program = programViewModel.getProgramById(programId)
    program?.let {
        ProgramDetailScreen(
            programImage = it.programimage, name = it.name, description = it.description
        )
    }
}

@Composable
fun ProgramDetailScreen(
    programImage: String, name: String, description: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = programImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(RectangleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name,
            fontSize = 24.sp,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            fontSize = 16.sp,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}




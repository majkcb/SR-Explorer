package com.example.sr_kodtest.data.connection

data class ProgramDTO(
    val id: Int,
    val name: String,
    val description: String,
    val programimage: String,
    val broadcastinfo: String
)

data class ProgramsResponse(
    val programs: List<ProgramDTO>
)



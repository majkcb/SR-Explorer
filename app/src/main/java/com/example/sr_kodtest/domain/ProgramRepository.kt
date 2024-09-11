package com.example.sr_kodtest.domain

import arrow.core.Either
import com.example.sr_kodtest.R
import com.example.sr_kodtest.data.ProgramDatasource
import com.example.sr_kodtest.models.Program
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ProgramRepository {
    suspend fun getPrograms(): Either<Int, List<Program>>
}

class ProgramRepositoryImpl @Inject constructor(
    private val programDatasource: ProgramDatasource
) : ProgramRepository {
    override suspend fun getPrograms(): Either<Int, List<Program>> = withContext(Dispatchers.IO) {
        return@withContext programDatasource.getPrograms().map { program ->
            program.map {
                Program(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    programimage = it.programimage,
                    broadcastinfo = it.broadcastinfo
                )
            }
        }.mapLeft {
            R.string.could_not_get_programs
        }
    }
}

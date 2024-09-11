package com.example.sr_kodtest.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.sr_kodtest.data.connection.ProgramDTO
import com.example.sr_kodtest.data.connection.SRApi
import javax.inject.Inject
import javax.inject.Singleton

interface ProgramDatasource {
    suspend fun getPrograms(): Either<Errors, List<ProgramDTO>>
    sealed class Errors {
        data object NetworkError : Errors()
    }
}

@Singleton
class ProgramDatasourceImpl @Inject constructor(
    private val api: SRApi
) : ProgramDatasource {
    override suspend fun getPrograms(): Either<ProgramDatasource.Errors, List<ProgramDTO>> {
        return try {
            val response = api.getPrograms()
            response.programs.right()
        } catch (e: Exception) {
            ProgramDatasource.Errors.NetworkError.left()
        }
    }
}


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
        data object ServerError : Errors()
        data object TimeoutError : Errors()
        data object InvalidResponse : Errors()
    }
}

@Singleton
class ProgramDatasourceImpl @Inject constructor(
    private val api: SRApi
) : ProgramDatasource {
    override suspend fun getPrograms(): Either<ProgramDatasource.Errors, List<ProgramDTO>> {
        val response = api.getPrograms()

        return if (response.isSuccessful) {
            response.body()?.programs?.right() ?: ProgramDatasource.Errors.InvalidResponse.left()
        } else {
            when (response.code()) {
                500 -> ProgramDatasource.Errors.ServerError.left()
                408 -> ProgramDatasource.Errors.TimeoutError.left()
                else -> ProgramDatasource.Errors.NetworkError.left()
            }
        }
    }
}


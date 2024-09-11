package com.example.sr_kodtest.data

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.sr_kodtest.connection.ProgramDTO
import com.example.sr_kodtest.connection.SRApi
import javax.inject.Inject
import javax.inject.Singleton

interface ProgramDatasource {
    suspend fun getPrograms(): Either<Errors, List<ProgramDTO>>
    sealed class Errors {
        data object NetworkError : Errors()
        data object ServerError : Errors()
    }
}

@Singleton
class ProgramDatasourceImpl @Inject constructor(
    private val api: SRApi
) : ProgramDatasource {
    override suspend fun getPrograms(): Either<ProgramDatasource.Errors, List<ProgramDTO>> {
        return try {
            val response = api.getPrograms()
            Log.d("Datasource", "Fetched programs: ${response.programs}")
            response.programs.right()
        } catch (e: Exception) {
            Log.e("Error", "Network or server error occurred", e)
            ProgramDatasource.Errors.NetworkError.left()
        }
    }
}


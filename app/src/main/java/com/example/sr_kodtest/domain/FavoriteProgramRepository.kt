package com.example.sr_kodtest.domain

import com.example.sr_kodtest.data.roomDB.FavoriteProgram
import com.example.sr_kodtest.data.roomDB.FavoriteProgramDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteProgramRepository @Inject constructor(
    private val favoriteProgramDAO: FavoriteProgramDAO
) {
    suspend fun addFavoriteProgram(programName: String) {
        favoriteProgramDAO.addFavoriteProgram(FavoriteProgram(programName))
    }

    suspend fun deleteFavoriteProgram(programName: String) {
        favoriteProgramDAO.deleteFavoriteProgram(programName)
    }

    fun getFavoritePrograms(): Flow<List<FavoriteProgram>> {
        return favoriteProgramDAO.getFavoritePrograms()
    }
}
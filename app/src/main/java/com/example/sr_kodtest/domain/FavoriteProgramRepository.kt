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
    suspend fun addFavoriteProgram(programId: Int) {
        favoriteProgramDAO.addFavoriteProgram(FavoriteProgram(programId))
    }

    suspend fun deleteFavoriteProgram(programId: Int) {
        favoriteProgramDAO.deleteFavoriteProgram(programId)
    }

    fun getFavoritePrograms(): Flow<List<FavoriteProgram>> {
        return favoriteProgramDAO.getFavoritePrograms()
    }
}
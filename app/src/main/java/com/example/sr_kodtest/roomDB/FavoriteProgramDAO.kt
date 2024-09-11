package com.example.sr_kodtest.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProgramDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteProgram(favoriteProgram: FavoriteProgram)

    @Query("SELECT * FROM favorites")
    fun getFavoritePrograms(): Flow<List<FavoriteProgram>>

    @Query("DELETE FROM favorites WHERE programName = :programName")
    suspend fun deleteFavoriteProgram(programName: String)

}
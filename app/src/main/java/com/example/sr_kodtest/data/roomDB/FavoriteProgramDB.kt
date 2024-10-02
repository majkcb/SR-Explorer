package com.example.sr_kodtest.data.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteProgram::class], version = 2, exportSchema = false)
abstract class FavoriteProgramDB : RoomDatabase() {
    abstract fun favoriteProgramDao(): FavoriteProgramDAO
}
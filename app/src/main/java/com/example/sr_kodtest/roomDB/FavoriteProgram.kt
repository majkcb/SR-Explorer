package com.example.sr_kodtest.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
class FavoriteProgram(
    @PrimaryKey val programName: String
)
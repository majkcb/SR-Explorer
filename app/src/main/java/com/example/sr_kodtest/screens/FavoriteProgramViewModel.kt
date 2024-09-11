package com.example.sr_kodtest.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sr_kodtest.domain.FavoriteProgramRepository
import com.example.sr_kodtest.data.roomDB.FavoriteProgram
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteProgramViewModel @Inject constructor(
    private val favoriteProgramRepository: FavoriteProgramRepository
) : ViewModel() {

    private val _favoritePrograms = MutableStateFlow<List<FavoriteProgram>>(emptyList())
    val favoritePrograms: StateFlow<List<FavoriteProgram>> = _favoritePrograms

    init {
        viewModelScope.launch {
            favoriteProgramRepository.getFavoritePrograms().collect { programs ->
                _favoritePrograms.value = programs
            }
        }
    }

    fun addFavoriteProgram(programName: String) {
        viewModelScope.launch {
            favoriteProgramRepository.addFavoriteProgram(programName)
        }
    }

    fun deleteFavoriteProgram(programName: String) {
        viewModelScope.launch {
            favoriteProgramRepository.deleteFavoriteProgram(programName)
        }
    }

}
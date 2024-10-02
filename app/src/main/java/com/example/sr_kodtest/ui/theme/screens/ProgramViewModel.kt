package com.example.sr_kodtest.ui.theme.screens

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sr_kodtest.data.roomDB.FavoriteProgram
import com.example.sr_kodtest.domain.FavoriteProgramRepository
import com.example.sr_kodtest.domain.ProgramRepository
import com.example.sr_kodtest.domain.models.Program
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(
    private val programRepository: ProgramRepository,
    private val favoriteProgramRepository: FavoriteProgramRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProgramUiState())
    val uiState: StateFlow<ProgramUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            programRepository.getPrograms().fold({ errorStringId ->
                _uiState.update { it.copy(isLoading = false, errorMessage = errorStringId) }
            }, { programs ->
                _uiState.update {
                    it.copy(
                        isLoading = false, programs = programs, errorMessage = null
                    )
                }
            })
        }

        viewModelScope.launch {
            favoriteProgramRepository.getFavoritePrograms().collect { favorites ->
                _uiState.update { currentState ->
                    currentState.copy(favoritePrograms = favorites)
                }
            }
        }
    }

    fun getProgramById(programId: Int): Program? {
        return _uiState.value.programs.find { it.id == programId }
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

data class ProgramUiState(
    val programs: List<Program> = emptyList(),
    val isLoading: Boolean = false,
    val selectedProgram: Program? = null,
    @StringRes val errorMessage: Int? = null,
    val favoritePrograms: List<FavoriteProgram> = emptyList()
)

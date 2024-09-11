package com.example.sr_kodtest.program

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sr_kodtest.domain.ProgramRepository
import com.example.sr_kodtest.models.Program
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(
    private val programRepository: ProgramRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProgramUiState())
    val uiState: StateFlow<ProgramUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
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
    }

    fun getProgramById(programId: Int): Program? {
        return _uiState.value.programs.find { it.id == programId }
    }


}

data class ProgramUiState(
    val programs: List<Program> = emptyList(),
    val isLoading: Boolean = false,
    val selectedProgram: Program? = null,
    @StringRes val errorMessage: Int? = null
)
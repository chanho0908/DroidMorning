package com.peto.droidmorning.exam.complete.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.repository.ExamRepository
import com.peto.droidmorning.exam.complete.model.ExamCompleteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExamCompleteViewModel(
    private val examRepository: ExamRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExamCompleteUiState())
    val uiState: StateFlow<ExamCompleteUiState> = _uiState.asStateFlow()

    fun loadExamDetail(examId: Long) {
        viewModelScope.launch {
            examRepository
                .fetchExamDetail(examId)
                .onSuccess { examDetails ->
                    _uiState.update {
                        it.updateExamDetails(examDetails)
                    }
                }
        }
    }
}

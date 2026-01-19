package com.peto.droidmorning.exam.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peto.droidmorning.domain.repository.ExamRepository
import com.peto.droidmorning.exam.detail.model.ExamDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExamDetailViewModel(
    private val examRepository: ExamRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExamDetailUiState())
    val uiState: StateFlow<ExamDetailUiState> = _uiState.asStateFlow()

    fun loadExamDetail(examId: Long) {
        viewModelScope.launch {
            examRepository
                .fetchExamDetail(examId)
                .onSuccess { examDetail ->
                    _uiState.update {
                        it.updateExamQuestions(examDetail)
                    }
                }
        }
    }
}

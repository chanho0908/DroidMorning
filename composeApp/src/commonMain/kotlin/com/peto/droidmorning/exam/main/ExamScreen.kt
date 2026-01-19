package com.peto.droidmorning.exam.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peto.droidmorning.common.ObserveAsEvents
import com.peto.droidmorning.designsystem.component.ConfirmDialog
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.exam.main.component.ExamCreateTab
import com.peto.droidmorning.exam.main.component.ExamHistoryTab
import com.peto.droidmorning.exam.main.model.ExamTab
import com.peto.droidmorning.exam.main.model.ExamUiEvent
import com.peto.droidmorning.exam.main.model.ExamUiState
import com.peto.droidmorning.exam.main.vm.ExamViewModel
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.exam_delete_confirm_message
import droidmorning.composeapp.generated.resources.exam_delete_confirm_title
import droidmorning.composeapp.generated.resources.exam_delete_success
import droidmorning.composeapp.generated.resources.exam_tab_create
import droidmorning.composeapp.generated.resources.exam_tab_history
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExamScreen(
    onNavigateToExamProgress: (questionCount: Int, categories: List<Category>) -> Unit,
    onNavigateToExamResult: (Long) -> Unit,
    viewModel: ExamViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val deleteSuccessMessage = stringResource(Res.string.exam_delete_success)

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is ExamUiEvent.NavigateToExamProgress -> {
                onNavigateToExamProgress(event.questionCount, event.categories)
            }
            is ExamUiEvent.NavigateToExamResult -> {
                onNavigateToExamResult(event.examId)
            }
            ExamUiEvent.ShowDeleteSuccessMessage -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(deleteSuccessMessage)
                }
            }
        }
    }

    ExamScreenContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onSelectTab = viewModel::selectTab,
        onSelectQuestionCount = viewModel::selectQuestionCount,
        onToggleCategory = viewModel::toggleCategory,
        onStartExam = viewModel::startExam,
        onOpenExamHistory = viewModel::openExamHistory,
        onDeleteExam = viewModel::showDeleteConfirmation,
        onConfirmDelete = viewModel::deleteExam,
        onDismissDeleteDialog = viewModel::hideDeleteConfirmation,
    )
}

@Composable
private fun ExamScreenContent(
    uiState: ExamUiState,
    snackbarHostState: SnackbarHostState,
    onSelectTab: (ExamTab) -> Unit,
    onSelectQuestionCount: (Int) -> Unit,
    onToggleCategory: (Category) -> Unit,
    onStartExam: () -> Unit,
    onOpenExamHistory: (Long) -> Unit,
    onDeleteExam: (Long) -> Unit,
    onConfirmDelete: () -> Unit,
    onDismissDeleteDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            TabRow(selectedTabIndex = uiState.selectedTab.ordinal) {
                Tab(
                    selected = uiState.selectedTab == ExamTab.CREATE,
                    onClick = { onSelectTab(ExamTab.CREATE) },
                    text = { Text(stringResource(Res.string.exam_tab_create)) },
                )
                Tab(
                    selected = uiState.selectedTab == ExamTab.HISTORY,
                    onClick = { onSelectTab(ExamTab.HISTORY) },
                    text = { Text(stringResource(Res.string.exam_tab_history)) },
                )
            }

            when (uiState.selectedTab) {
                ExamTab.CREATE ->
                    ExamCreateTab(
                        state = uiState.createState,
                        onSelectQuestionCount = onSelectQuestionCount,
                        onToggleCategory = onToggleCategory,
                        onStartExam = onStartExam,
                    )
                ExamTab.HISTORY ->
                    ExamHistoryTab(
                        state = uiState.historyState,
                        onOpenExamHistory = onOpenExamHistory,
                        onDeleteExam = onDeleteExam,
                    )
            }
        }
    }

    if (uiState.examToDelete != null) {
        ConfirmDialog(
            onDismissRequest = onDismissDeleteDialog,
            onConfirm = onConfirmDelete,
            title = stringResource(Res.string.exam_delete_confirm_title),
            message = stringResource(Res.string.exam_delete_confirm_message),
        )
    }
}

@Preview
@Composable
private fun ExamScreenPreview(
    @PreviewParameter(com.peto.droidmorning.exam.main.preview.ExamUiStatePreviewProvider::class)
    uiState: ExamUiState,
) {
    AppTheme {
        ExamScreenContent(
            uiState = uiState,
            snackbarHostState = remember { SnackbarHostState() },
            onSelectTab = {},
            onSelectQuestionCount = {},
            onToggleCategory = {},
            onStartExam = {},
            onOpenExamHistory = {},
            onDeleteExam = {},
            onConfirmDelete = {},
            onDismissDeleteDialog = {},
        )
    }
}

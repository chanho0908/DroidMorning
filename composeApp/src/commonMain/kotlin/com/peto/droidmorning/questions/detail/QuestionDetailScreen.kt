package com.peto.droidmorning.questions.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peto.droidmorning.common.ObserveAsEvents
import com.peto.droidmorning.designsystem.component.AppPrimaryButton
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.questions.detail.component.AddAnswerBottomSheet
import com.peto.droidmorning.questions.detail.component.AnswerHistory
import com.peto.droidmorning.questions.detail.component.MyAnswer
import com.peto.droidmorning.questions.detail.component.QuestionInfo
import com.peto.droidmorning.questions.detail.model.AnswerUiModel
import com.peto.droidmorning.questions.detail.model.QuestionDetailUiEvent
import com.peto.droidmorning.questions.detail.model.QuestionDetailUiState
import com.peto.droidmorning.questions.detail.model.QuestionUpdateResult
import com.peto.droidmorning.questions.detail.preview.QuestionDetailPreviewParameterProvider
import com.peto.droidmorning.questions.detail.vm.QuestionDetailViewModel
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.add_answer
import droidmorning.composeapp.generated.resources.back
import droidmorning.composeapp.generated.resources.favorite
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetailScreen(
    questionId: Long,
    onNavigateBack: (QuestionUpdateResult) -> Unit,
    viewModel: QuestionDetailViewModel = koinViewModel { parametersOf(questionId) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddAnswerSheet by remember { mutableStateOf(false) }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is QuestionDetailUiEvent.NavigateBack -> {
                onNavigateBack(event.result)
            }
        }
    }

    QuestionDetailScreenContent(
        uiState = uiState,
        onNavigateBack = viewModel::onNavigateBack,
        onToggleFavorite = viewModel::onToggleFavorite,
        onShowAddAnswerSheet = { showAddAnswerSheet = true },
        onUpdateAnswer = { answer, content -> viewModel.onUpdateAnswer(answer, content) },
        onDeleteAnswer = { answer -> viewModel.onDeleteAnswer(answer) },
    )

    if (showAddAnswerSheet) {
        AddAnswerBottomSheet(
            draftAnswer = uiState.draftAnswer,
            onDraftAnswerChange = viewModel::onDraftAnswerChange,
            onDismiss = { showAddAnswerSheet = false },
            onSave = { content ->
                viewModel.onAddAnswer(content)
                showAddAnswerSheet = false
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuestionDetailScreenContent(
    uiState: QuestionDetailUiState,
    onNavigateBack: () -> Unit,
    onShowAddAnswerSheet: () -> Unit,
    onToggleFavorite: () -> Unit,
    onUpdateAnswer: (AnswerUiModel.Current, String) -> Unit,
    onDeleteAnswer: (AnswerUiModel) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back),
                        )
                    }
                },
                actions = {
                    val question = uiState.question
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector =
                                if (question.isLiked) {
                                    Icons.Filled.Star
                                } else {
                                    Icons.Filled.StarBorder
                                },
                            contentDescription = stringResource(Res.string.favorite),
                            tint =
                                if (question.isLiked) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                },
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
            )
        },
        bottomBar = {
            AppPrimaryButton(
                text = stringResource(Res.string.add_answer),
                onClick = onShowAddAnswerSheet,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(Dimen.spacingLg),
                icon = Icons.Filled.Edit,
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(vertical = Dimen.spacingBase),
                        verticalArrangement = Arrangement.spacedBy(Dimen.spacingXl),
                    ) {
                        QuestionInfo(
                            title = uiState.question.title,
                            category = uiState.question.category,
                            isSolved = uiState.question.isSolved,
                            modifier = Modifier.padding(horizontal = Dimen.spacingBase),
                        )

                        HorizontalDivider()

                        MyAnswer(
                            answer = uiState.currentAnswer,
                            onUpdateAnswer = onUpdateAnswer,
                            onDeleteAnswer = { onDeleteAnswer(it) },
                            modifier = Modifier.padding(horizontal = Dimen.spacingBase),
                        )

                        if (uiState.historyAnswers.isNotEmpty()) {
                            HorizontalDivider()

                            AnswerHistory(
                                historyAnswers = uiState.historyAnswers,
                                onDeleteAnswer = { onDeleteAnswer(it) },
                                modifier = Modifier.padding(horizontal = Dimen.spacingBase),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuestionDetailScreenContentPreview(
    @PreviewParameter(QuestionDetailPreviewParameterProvider::class)
    uiState: QuestionDetailUiState,
) {
    AppTheme {
        QuestionDetailScreenContent(
            uiState = uiState,
            onNavigateBack = {},
            onShowAddAnswerSheet = {},
            onToggleFavorite = {},
            onUpdateAnswer = { _, _ -> },
            onDeleteAnswer = {},
        )
    }
}

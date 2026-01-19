package com.peto.droidmorning.exam.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peto.droidmorning.common.ObserveAsEvents
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.exam.progress.component.ExamAnswerInput
import com.peto.droidmorning.exam.progress.component.ExamNavigationButtons
import com.peto.droidmorning.exam.progress.component.ExamQuestionHeader
import com.peto.droidmorning.exam.progress.model.ExamProgressUiEvent
import com.peto.droidmorning.exam.progress.model.ExamProgressUiState
import com.peto.droidmorning.exam.progress.preview.ExamProgressUiStatePreviewProvider
import com.peto.droidmorning.exam.progress.vm.ExamProgressViewModel
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.back
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamProgressScreen(
    questionCount: Int,
    categories: List<Category>,
    onNavigateToComplete: (Long) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ExamProgressViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadExamQuestions(questionCount, categories)
    }

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is ExamProgressUiEvent.NavigateToComplete ->
                onNavigateToComplete(event.examId)

            ExamProgressUiEvent.NavigateBack -> onNavigateBack()
        }
    }

    ExamProgressScreenContent(
        uiState = uiState,
        onCancelExam = viewModel::cancelExam,
        onAnswerChanged = viewModel::onAnswerChanged,
        onPreviousQuestion = viewModel::previousQuestion,
        onNextQuestion = viewModel::nextQuestion,
        onSubmitExam = viewModel::submitExam,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExamProgressScreenContent(
    uiState: ExamProgressUiState,
    onCancelExam: () -> Unit,
    onAnswerChanged: (Long, String) -> Unit,
    onPreviousQuestion: () -> Unit,
    onNextQuestion: () -> Unit,
    onSubmitExam: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier =
            modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onCancelExam) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back),
                        )
                    }
                },
                actions = {
                    Text(
                        text =
                            "${uiState.currentQuestionIndex + 1}/${uiState.questions.size}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(end = 16.dp),
                    )
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
            ) {
                LinearProgressIndicator(
                    progress = { uiState.progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    drawStopIndicator = {},
                )

                uiState.currentQuestion?.let { currentQuestion ->
                    Column(
                        modifier =
                            Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        ExamQuestionHeader(
                            category = currentQuestion.category,
                            questionContent = currentQuestion.content,
                            modifier = Modifier.padding(16.dp),
                        )

                        HorizontalDivider()

                        ExamAnswerInput(
                            answer = uiState.exams[currentQuestion.questionId],
                            onAnswerChange = { answer ->
                                onAnswerChanged(currentQuestion.questionId, answer)
                            },
                            modifier = Modifier.padding(16.dp),
                        )
                    }

                    ExamNavigationButtons(
                        isFirstQuestion = uiState.isFirstQuestion,
                        isLastQuestion = uiState.isLastQuestion,
                        canGoNext = uiState.canGoNext,
                        canSubmit = uiState.canSubmit,
                        onPreviousClick = onPreviousQuestion,
                        onNextClick = onNextQuestion,
                        onSubmitClick = onSubmitExam,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExamProgressScreenWithAnswerPreview(
    @PreviewParameter(ExamProgressUiStatePreviewProvider::class) uiState: ExamProgressUiState,
) {
    AppTheme {
        ExamProgressScreenContent(
            uiState = uiState,
            onCancelExam = {},
            onAnswerChanged = { _, _ -> },
            onPreviousQuestion = {},
            onNextQuestion = {},
            onSubmitExam = {},
        )
    }
}

@Preview
@Composable
private fun ExamProgressScreenWithoutAnswerPreview() {
    AppTheme {
        ExamProgressScreenContent(
            uiState =
                ExamProgressUiState(
                    questions =
                        listOf(
                            com.peto.droidmorning.domain.model.exam.ExamQuestion(
                                questionId = 1L,
                                content = "Android에서 ViewModel의 역할은 무엇인가요?",
                                category = Category.Android,
                            ),
                        ),
                    currentQuestionIndex = 0,
                ),
            onCancelExam = {},
            onAnswerChanged = { _, _ -> },
            onPreviousQuestion = {},
            onNextQuestion = {},
            onSubmitExam = {},
        )
    }
}

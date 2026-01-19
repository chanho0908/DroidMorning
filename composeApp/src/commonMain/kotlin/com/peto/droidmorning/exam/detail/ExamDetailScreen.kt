package com.peto.droidmorning.exam.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peto.droidmorning.designsystem.component.ExamQuestionCard
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.exam.detail.model.ExamDetailUiState
import com.peto.droidmorning.exam.detail.preview.ExamDetailUiStatePreviewParameterProvider
import com.peto.droidmorning.exam.detail.vm.ExamDetailViewModel
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.back
import droidmorning.composeapp.generated.resources.exam_question_count_format
import droidmorning.composeapp.generated.resources.exam_result_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamDetailScreen(
    examId: Long,
    onNavigateBack: () -> Unit,
    viewModel: ExamDetailViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(examId) {
        viewModel.loadExamDetail(examId)
    }

    ExamDetailScreenContent(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExamDetailScreenContent(
    uiState: ExamDetailUiState,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.exam_result_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back),
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            contentPadding = PaddingValues(Dimen.spacingBase),
            verticalArrangement = Arrangement.spacedBy(Dimen.spacingBase),
        ) {
            item {
                ExamDetailHeader(
                    questionCount = uiState.examQuestionCount,
                )
            }

            itemsIndexed(uiState.examQuestions) { index, question ->
                ExamQuestionCard(
                    questionNumber = index + 1,
                    examDetail = question,
                )
            }
        }
    }
}

@Composable
private fun ExamDetailHeader(
    questionCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimen.spacingSm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            modifier = Modifier.size(Dimen.iconSm),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
        Text(
            text = "$questionCount${stringResource(Res.string.exam_question_count_format)}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        )
    }
}

@Preview
@Composable
private fun ExamDetailScreenContentPreview(
    @PreviewParameter(ExamDetailUiStatePreviewParameterProvider::class)
    uiState: ExamDetailUiState,
) {
    AppTheme {
        ExamDetailScreenContent(
            uiState = uiState,
            onNavigateBack = {},
        )
    }
}

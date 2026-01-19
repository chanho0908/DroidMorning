package com.peto.droidmorning.exam.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peto.droidmorning.designsystem.component.ExamQuestionCard
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.exam.complete.model.ExamCompleteUiState
import com.peto.droidmorning.exam.complete.preview.ExamCompleteUiStatePreviewParameterProvider
import com.peto.droidmorning.exam.complete.vm.ExamCompleteViewModel
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.exam_result_back_to_questions
import droidmorning.composeapp.generated.resources.exam_result_complete_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExamCompleteScreen(
    examId: Long,
    onNavigateToQuestions: () -> Unit,
    viewModel: ExamCompleteViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(examId) {
        viewModel.loadExamDetail(examId)
    }

    ExamCompleteScreenContent(
        uiState = uiState,
        onNavigateToQuestions = onNavigateToQuestions,
    )
}

@Composable
private fun ExamCompleteScreenContent(
    uiState: ExamCompleteUiState,
    onNavigateToQuestions: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
            ) {
                Button(
                    onClick = onNavigateToQuestions,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(Dimen.spacingBase),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                ) {
                    Text(
                        text = stringResource(Res.string.exam_result_back_to_questions),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
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
            item { ExamCompleteHeader() }

            itemsIndexed(uiState.examDetails) { index, examDetail ->
                ExamQuestionCard(
                    questionNumber = index + 1,
                    examDetail = examDetail,
                )
            }
        }
    }
}

@Composable
private fun ExamCompleteHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimen.spacingSm),
    ) {
        Box(
            modifier =
                Modifier
                    .size(80.dp)
                    .background(
                        color = Color(0xFFB8E6D5),
                        shape = CircleShape,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF34A853),
            )
        }

        Text(
            text = stringResource(Res.string.exam_result_complete_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun ExamCompleteScreenPreview(
    @PreviewParameter(ExamCompleteUiStatePreviewParameterProvider::class)
    uiState: ExamCompleteUiState,
) {
    AppTheme {
        ExamCompleteScreenContent(
            uiState = uiState,
            onNavigateToQuestions = {},
        )
    }
}

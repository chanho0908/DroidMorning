package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.peto.droidmorning.designsystem.extension.color
import com.peto.droidmorning.designsystem.generated.resources.DesignRes
import com.peto.droidmorning.designsystem.generated.resources.exam_question_card_answer_label
import com.peto.droidmorning.designsystem.generated.resources.exam_question_card_question_label
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamDetail
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExamQuestionCard(
    questionNumber: Int,
    examDetail: ExamDetail,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimen.radiusBase),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = Dimen.cardElevation,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(Dimen.spacingBase),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CategoryBadge(
                    category = examDetail.questionCategory,
                    categoryColor = examDetail.questionCategory.color,
                )
                Text(
                    text = "#$questionNumber",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                )
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Dimen.radiusMd))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                        .padding(Dimen.spacingBase),
                verticalArrangement = Arrangement.spacedBy(Dimen.spacingSm),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimen.spacingXs),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(Dimen.iconXs),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = stringResource(DesignRes.string.exam_question_card_question_label),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                Text(
                    text = examDetail.questionTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(Dimen.spacingMd))

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimen.spacingBase)
                        .padding(bottom = Dimen.spacingBase),
                verticalArrangement = Arrangement.spacedBy(Dimen.spacingSm),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimen.spacingXs),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(Dimen.iconXs),
                        tint = MaterialTheme.colorScheme.onSecondary,
                    )
                    Text(
                        text = stringResource(DesignRes.string.exam_question_card_answer_label),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                }

                Text(
                    text = examDetail.userAnswer,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExamQuestionCardWithAnswerPreview() {
    AppTheme {
        ExamQuestionCard(
            questionNumber = 1,
            examDetail =
                ExamDetail(
                    examItemId = 1L,
                    examId = 1L,
                    questionId = 1L,
                    userAnswer = "ViewModel은 UI 관련 데이터를 관리하고, 화면 회전과 같은 구성 변경에도 데이터를 유지하는 역할을 합니다.",
                    questionTitle = "Android에서 ViewModel의 역할은 무엇인가요?",
                    questionCategory = Category.Android,
                    questionSourceUrl = "https://example.com/question1",
                ),
        )
    }
}

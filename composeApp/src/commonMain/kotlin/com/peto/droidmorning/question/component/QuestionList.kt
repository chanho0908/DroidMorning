package com.peto.droidmorning.question.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.peto.droidmorning.designsystem.component.QuestionCard
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.domain.model.Category
import com.peto.droidmorning.domain.model.Question
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlin.time.Instant

@Composable
fun QuestionList(
    questions: ImmutableList<Question>,
    onQuestionClick: (Long) -> Unit,
    onLikeToggle: (Long) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(vertical = Dimen.spacingSm),
        verticalArrangement = Arrangement.spacedBy(Dimen.spacingMd),
        modifier = modifier,
    ) {
        items(
            items = questions,
            key = { it.id },
        ) { question ->
            QuestionCard(
                title = question.title,
                category = question.category,
                isSolved = question.isSolved,
                isLiked = question.isLiked,
                onClick = { onQuestionClick(question.id) },
                onLikeClick = { onLikeToggle(question.id) },
            )
        }
    }
}

@Preview
@Composable
private fun QuestionListEmptyPreview() {
    AppTheme {
        QuestionList(
            questions = persistentListOf(),
            onQuestionClick = {},
            onLikeToggle = {},
        )
    }
}

@Preview
@Composable
private fun QuestionListSinglePreview() {
    AppTheme {
        QuestionList(
            questions =
                persistentListOf(
                    Question(
                        id = 1L,
                        title = "Kotlin의 data class는 무엇인가요?",
                        category = Category.Kotlin,
                        sourceUrl = "https://example.com",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = false,
                        isLiked = false,
                    ),
                ),
            onQuestionClick = {},
            onLikeToggle = {},
        )
    }
}

@Preview
@Composable
private fun QuestionListMultiplePreview() {
    AppTheme {
        QuestionList(
            questions =
                persistentListOf(
                    Question(
                        id = 1L,
                        title = "Kotlin의 data class는 무엇인가요?",
                        category = Category.Kotlin,
                        sourceUrl = "https://example.com",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = true,
                        isLiked = true,
                    ),
                    Question(
                        id = 2L,
                        title = "Coroutine의 launch와 async의 차이점은?",
                        category = Category.Coroutine,
                        sourceUrl = "https://example.com",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = false,
                        isLiked = true,
                    ),
                    Question(
                        id = 3L,
                        title = "Android의 ViewModel은 왜 사용하나요?",
                        category = Category.Android,
                        sourceUrl = "https://example.com",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = true,
                        isLiked = false,
                    ),
                    Question(
                        id = 4L,
                        title = "Jetpack Compose의 상태 관리 방법은?",
                        category = Category.Compose,
                        sourceUrl = "https://example.com",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = false,
                        isLiked = false,
                    ),
                    Question(
                        id = 5L,
                        title = "OOP의 SOLID 원칙에 대해 설명해주세요",
                        category = Category.OOP,
                        sourceUrl = "https://example.com",
                        createdAt = Instant.fromEpochMilliseconds(0),
                        updatedAt = Instant.fromEpochMilliseconds(0),
                        isSolved = false,
                        isLiked = false,
                    ),
                ),
            onQuestionClick = {},
            onLikeToggle = {},
        )
    }
}

package com.peto.droidmorning.questions.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peto.droidmorning.common.ObserveAsEvents
import com.peto.droidmorning.designsystem.component.AppSearchBar
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.domain.model.Category
import com.peto.droidmorning.questions.component.CategoryChips
import com.peto.droidmorning.questions.component.EmptyQuestion
import com.peto.droidmorning.questions.component.QuestionFilterChips
import com.peto.droidmorning.questions.component.QuestionList
import com.peto.droidmorning.questions.vm.QuestionUiEvent
import com.peto.droidmorning.questions.vm.QuestionUiState
import com.peto.droidmorning.questions.vm.QuestionViewModel
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.question_empty_search
import droidmorning.composeapp.generated.resources.question_empty_state
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QuestionScreen(
    viewModel: QuestionViewModel = koinViewModel(),
    onNavigateToDetail: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(
        uiState.searchQuery,
        uiState.selectedCategories,
        uiState.showSolvedOnly,
        uiState.showLikedOnly,
    ) {
        if (listState.firstVisibleItemIndex > 0) {
            listState.scrollToItem(0)
        }
    }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is QuestionUiEvent.NavigateToQuestionDetail -> {
                onNavigateToDetail(event.questionId)
            }
            is QuestionUiEvent.ShowError -> {
            }
            is QuestionUiEvent.ScrollToTop -> {
                coroutineScope.launch {
                    listState.scrollToItem(0)
                }
            }
        }
    }

    QuestionScreenContent(
        uiState = uiState,
        listState = listState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onCategoryToggle = viewModel::onCategoryToggle,
        onToggleCategoryFilters = viewModel::onToggleCategoryFilters,
        onSolvedFilterToggle = viewModel::onSolvedFilterToggle,
        onLikedFilterToggle = viewModel::onLikedFilterToggle,
        onQuestionClick = onNavigateToDetail,
        onLikeToggle = viewModel::onLikeToggle,
    )
}

@Composable
private fun QuestionScreenContent(
    uiState: QuestionUiState,
    listState: LazyListState,
    onSearchQueryChange: (String) -> Unit,
    onCategoryToggle: (Category) -> Unit,
    onToggleCategoryFilters: () -> Unit,
    onSolvedFilterToggle: () -> Unit,
    onLikedFilterToggle: () -> Unit,
    onQuestionClick: (Long) -> Unit,
    onLikeToggle: (Long) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(Dimen.spacingMd),
    ) {
        AppSearchBar(
            query = uiState.searchQuery.value,
            onQueryChange = onSearchQueryChange,
            onSearch = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(Dimen.spacingMd))

        QuestionFilterChips(
            selectedCategories = uiState.selectedCategories,
            showSolvedOnly = uiState.showSolvedOnly,
            showLikedOnly = uiState.showLikedOnly,
            onToggleCategoryFilters = onToggleCategoryFilters,
            onSolvedFilterToggle = onSolvedFilterToggle,
            onLikedFilterToggle = onLikedFilterToggle,
        )

        Spacer(modifier = Modifier.height(Dimen.spacingMd))

        if (uiState.showCategoryFilters) {
            CategoryChips(
                selectedCategories = uiState.selectedCategories,
                onCategoryToggle = onCategoryToggle,
            )

            Spacer(modifier = Modifier.height(Dimen.spacingMd))
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.filteredQuestions.isEmpty() && !uiState.isFiltering -> {
                    EmptyQuestion(
                        message =
                            if (!uiState.searchQuery.isEmpty()) {
                                stringResource(Res.string.question_empty_search)
                            } else {
                                stringResource(Res.string.question_empty_state)
                            },
                    )
                }
                else -> {
                    QuestionList(
                        questions = uiState.filteredQuestions,
                        onQuestionClick = onQuestionClick,
                        onLikeToggle = onLikeToggle,
                        listState = listState,
                    )
                }
            }

            if (uiState.isFiltering) {
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

package com.peto.droidmorning.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.history.HistoryScreen
import com.peto.droidmorning.profile.ProfileScreen
import com.peto.droidmorning.questions.list.QuestionScreen
import com.peto.droidmorning.test.TestScreen
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen(onNavigateToQuestionDetail: (Long) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf(BottomNavigationType.QUESTION) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
            )
        },
    ) { paddingValues ->
        MainContent(
            selectedTab = selectedTab,
            onNavigateToQuestionDetail = onNavigateToQuestionDetail,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun BottomNavigationBar(
    selectedTab: BottomNavigationType,
    onTabSelected: (BottomNavigationType) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        modifier = modifier,
    ) {
        CompositionLocalProvider(LocalRippleConfiguration provides null) {
            BottomNavigationType.entries.forEach { navType ->
                NavigationBarItem(
                    selected = selectedTab == navType,
                    onClick = { onTabSelected(navType) },
                    icon = {
                        Icon(
                            imageVector = navType.icon,
                            contentDescription = stringResource(navType.label),
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(navType.label),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                )
            }
        }
    }
}

@Composable
private fun MainContent(
    selectedTab: BottomNavigationType,
    onNavigateToQuestionDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when (selectedTab) {
            BottomNavigationType.QUESTION ->
                QuestionScreen(
                    onNavigateToDetail = onNavigateToQuestionDetail,
                )
            BottomNavigationType.TEST -> TestScreen()
            BottomNavigationType.HISTORY -> HistoryScreen()
            BottomNavigationType.PROFILE -> ProfileScreen()
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}

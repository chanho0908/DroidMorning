package com.peto.droidmorning.main.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.peto.droidmorning.main.BottomNavigationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _selectedTab =
        MutableStateFlow(
            savedStateHandle
                .get<String>(SELECTED_TAB_KEY)
                ?.let { BottomNavigationType.valueOf(it) }
                ?: BottomNavigationType.QUESTION,
        )
    val selectedTab = _selectedTab.asStateFlow()

    fun selectTab(tab: BottomNavigationType) {
        _selectedTab.value = tab
        savedStateHandle[SELECTED_TAB_KEY] = tab.name
    }

    companion object {
        private const val SELECTED_TAB_KEY = "selectedTab"
    }
}

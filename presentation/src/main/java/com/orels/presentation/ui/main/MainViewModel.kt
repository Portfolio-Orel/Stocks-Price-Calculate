package com.orels.presentation.ui.main

import PortfolioComparison
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(MainState())

    fun onTickerChange(ticker: String) {
        state = state.copy(ticker = ticker)
    }

    fun onFileSelected(uri: Uri, context: Context) {
        viewModelScope.launch {
            PortfolioComparison().run(uri = uri, context = context)
        }
    }
}
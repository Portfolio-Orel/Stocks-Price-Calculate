package com.orels.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orels.domain.use_case.Resource
import com.orels.domain.use_case.get_stock.GetStockUseCase
import com.orels.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getStockUseCase: GetStockUseCase,
) : ViewModel() {
    var state by mutableStateOf(MainState())

    init {
        setStock("AAPL")
    }

    fun setStock(ticker: String) {
        getStockUseCase(ticker = ticker).onEach { result ->
            state = when (result) {
                is Resource.Error -> state.copy(
                    isLoading = false, error = R.string.error_occurred
                )
                is Resource.Loading -> state.copy(isLoading = true)
                is Resource.Success -> state.copy(isLoading = false, selectedStock = result.data)
            }
        }.launchIn(viewModelScope)
    }
}
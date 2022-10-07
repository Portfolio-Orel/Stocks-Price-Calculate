package com.orels.presentation.ui.stock_price

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orels.domain.model.entities.Stock
import com.orels.domain.model.entities.StockResultsData
import com.orels.domain.use_case.Resource
import com.orels.domain.use_case.get_stock.GetStockUseCase
import com.orels.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * @author Orel Zilberman
 * 06/10/2022
 */

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val getStockUseCase: GetStockUseCase
) : ViewModel() {
    var state by mutableStateOf(ResultsState())

    fun setStock(ticker: String) {
        if (ticker.isEmpty()) {
            state = state.copy(error = R.string.error_empty_ticker_input)
            return
        }
        getStockUseCase(ticker = ticker).onEach { result ->
            state = when (result) {
                is Resource.Error -> state.copy(
                    isLoading = false, error = R.string.error_occurred
                )
                is Resource.Loading -> state.copy(isLoading = true)
                is Resource.Success -> {
                    val stockResultsData = state.stockResultsData
                    stockResultsData.startingPrice = result.data?.price ?: -1.0
                    result.data?.let { setFields(stockResultsData = stockResultsData, it) }
                    state.copy(
                        isLoading = false,
                        selectedStock = result.data,
                        stockResultsData = stockResultsData
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun setFields(stockResultsData: StockResultsData, stock: Stock) {
        state = state.copy(
            stockResultsDataFields = listOf(
                StockResultsDataFields(
                    title = R.string.years, onChange = {
                        val resultData = state.stockResultsData
                        resultData.years = it
                        setExpectedStockDetails()
                    }, maxCharacters = 2,
                    defaultValue = 5.0
                ),
                StockResultsDataFields(
                    title = R.string.pe, onChange = {
                        stockResultsData.expectedPE = it
                        setExpectedStockDetails()
                    }, maxCharacters = 3,
                    defaultValue = stock.trailingPE ?: 15.0
                ),
                StockResultsDataFields(
                    title = R.string.profit_margin, onChange = {
                        stockResultsData.expectedProfitMargin = it
                        setExpectedStockDetails()
                    }, maxCharacters = 3,
                    defaultValue = (stock.profitMargin ?: 0.0) * 100.0
                ),
                StockResultsDataFields(
                    title = R.string.shares_outstanding, onChange = {
                        stockResultsData.expectedSharesOutstanding = it
                        setExpectedStockDetails()
                    },
                    defaultValue = stock.sharesOutstanding ?: 10.0
                ),
                StockResultsDataFields(
                    title = R.string.growth_rate, onChange = {
                        stockResultsData.expectedAnnualGrowthRate = it
                        setExpectedStockDetails()
                    },
                    defaultValue = 10.0
                ),
                StockResultsDataFields(
                    title = R.string.minimum_irr, onChange = {
                        stockResultsData.expectedIRR = it
                        setExpectedStockDetails()
                    },
                    defaultValue = 15.0
                ),
                StockResultsDataFields(
                    title = R.string.shares_outstanding_reduction_rate, onChange = {
                        stockResultsData.expectedSharesOutstandingReduction = it
                        setExpectedStockDetails()
                    },
                    defaultValue = 0.0
                ),
                StockResultsDataFields(
                    title = R.string.projected_price, onChange = {
                        stockResultsData.startingPrice = it
                        setExpectedStockDetails()
                    },
                    defaultValue = stock.price ?: 100.0
                ),
            )
        )
    }

    private fun setExpectedStockDetails() {
        val tempState = state.selectedStock?.getExpectedDetails(state.stockResultsData)
            ?.let { state.copy(expectedResults = it) }
        if (tempState != null) {
            state = tempState
        }
    }
}

class StockResultsDataFields(
    @StringRes val title: Int,
    val onChange: (Double) -> Unit,
    val defaultValue: Double,
    val maxCharacters: Int = 100
)
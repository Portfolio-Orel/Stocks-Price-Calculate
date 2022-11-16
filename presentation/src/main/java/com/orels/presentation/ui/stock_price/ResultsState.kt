package com.orels.presentation.ui.stock_price

import androidx.annotation.StringRes
import com.orels.domain.model.entities.ExpectedStockDetails
import com.orels.domain.model.entities.Stock
import com.orels.domain.model.entities.StockResultsData

/**
 * @author Orel Zilberman
 * 06/10/2022
 */

data class ResultsState(
    val selectedStock: Stock? = null,
    val stockResultsData: StockResultsData? = null,
    val stockResultsDataFields: List<StockResultsDataFields> = emptyList(),
    val expectedResults: ExpectedStockDetails = ExpectedStockDetails(),

    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)
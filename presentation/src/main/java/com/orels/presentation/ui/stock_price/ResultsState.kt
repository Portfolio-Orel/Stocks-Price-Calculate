package com.orels.presentation.ui.stock_price

import androidx.annotation.StringRes
import com.orels.domain.model.entities.CaseType
import com.orels.domain.model.entities.ExpectedStockDetails
import com.orels.domain.model.entities.Stock
import com.orels.domain.model.entities.StockResultsData

/**
 * @author Orel Zilberman
 * 06/10/2022
 */

data class ResultsState(
    val selectedStock: Stock? = null,
    val stockResultsDataWorst: StockResultsData? = null,
    val stockResultsDataBase: StockResultsData? = null,
    val stockResultsDataBest: StockResultsData? = null,
    val stockResultsDataFields: Map<CaseType, List<StockResultsDataFields>> = emptyMap(),

    val expectedResultsWorst: ExpectedStockDetails = ExpectedStockDetails(),
    val expectedResultsBase: ExpectedStockDetails = ExpectedStockDetails(),
    val expectedResultsBest: ExpectedStockDetails = ExpectedStockDetails(),

    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)

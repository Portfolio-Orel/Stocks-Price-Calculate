package com.orels.presentation.ui.stock_price

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orels.domain.model.entities.CaseType
import com.orels.domain.model.entities.ExpectedStockDetails
import com.orels.domain.model.entities.Stock
import com.orels.domain.model.entities.StockResultsData
import com.orels.domain.model.entities.toWorstCase
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
                    val stockResultsDataWorst =
                        StockResultsData(result.data, caseType = CaseType.Worst)
                    val stockResultsDataBase = StockResultsData(result.data)
                    val stockResultsDataBest =
                        StockResultsData(result.data, caseType = CaseType.Best)

                    setExpectedStockDetails(
                        stock = result.data,
                        stockResultsData = stockResultsDataWorst,
                        caseType = CaseType.Worst
                    )
                    setExpectedStockDetails(
                        stock = result.data,
                        stockResultsData = stockResultsDataBase,
                        caseType = CaseType.Base
                    )
                    setExpectedStockDetails(
                        stock = result.data,
                        stockResultsData = stockResultsDataBest,
                        caseType = CaseType.Best
                    )

                    result.data?.let {
                        state = state.copy(
                            stockResultsDataFields = mapOf(
                                CaseType.Best to
                                        getFields(stockResultsData = stockResultsDataBest),
                                CaseType.Base to
                                        getFields(stockResultsData = stockResultsDataBase),
                                CaseType.Worst to
                                        getFields(stockResultsData = stockResultsDataWorst),
                            )
                        )
                    }
                    state.copy(
                        isLoading = false,
                        selectedStock = result.data,
                        stockResultsDataWorst = stockResultsDataWorst,
                        stockResultsDataBase = stockResultsDataBase,
                        stockResultsDataBest = stockResultsDataBest
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFields(stockResultsData: StockResultsData): List<StockResultsDataFields> =
        with(stockResultsData.toWorstCase()) {
            listOf(
                StockResultsDataFields(
                    title = R.string.years, onChange = { value, caseType ->
                        stockResultsData.years = value.toInt()
                        setExpectedStockDetails(caseType = caseType)
                    }, maxCharacters = 2,
                    defaultValue = "5"
                ),
                StockResultsDataFields(
                    title = R.string.pe, onChange = { value, caseType ->
                        stockResultsData.expectedPE = value.toInt()
                        setExpectedStockDetails(caseType = caseType)
                    }, maxCharacters = 3,
                    defaultValue = stockResultsData.expectedPE.removeDecimal()
                ),
                StockResultsDataFields(
                    title = R.string.profit_margin_short, onChange = { value, caseType ->
                        stockResultsData.expectedProfitMargin = value.toInt()
                        setExpectedStockDetails(caseType = caseType)
                    }, maxCharacters = 3,
                    defaultValue = stockResultsData.expectedProfitMargin.removeDecimal(),
                    trailingIcon = R.drawable.percentage_round
                ),
                StockResultsDataFields(
                    title = R.string.growth_rate_short, onChange = { value, caseType ->
                        stockResultsData.expectedAnnualGrowthRate = value.toInt()
                        setExpectedStockDetails(caseType = caseType)
                    },
                    defaultValue = stockResultsData.expectedAnnualGrowthRate.removeDecimal(),
                    trailingIcon = R.drawable.percentage_round
                ),
                StockResultsDataFields(
                    title = R.string.minimum_irr, onChange = { value, caseType ->
                        stockResultsData.expectedIRR = value.toInt()
                        setExpectedStockDetails(caseType = caseType)
                    },
                    defaultValue = stockResultsData.expectedIRR.removeDecimal(),
                    trailingIcon = R.drawable.percentage_round
                ),
                StockResultsDataFields(
                    title = R.string.shares_outstanding_reduction_rate_short,
                    onChange = { value, caseType ->
                        stockResultsData.expectedSharesOutstandingReduction = value.toInt()
                        setExpectedStockDetails(caseType = caseType)
                    },
                    defaultValue = stockResultsData.expectedSharesOutstandingReduction.removeDecimal(),
                    trailingIcon = R.drawable.percentage_round
                ),
                StockResultsDataFields(
                    title = R.string.projected_price_short,
                    onChange = { value, caseType ->
                        stockResultsData.startingPrice = value
                        setExpectedStockDetails(caseType = caseType)
                    },
                    defaultValue = stockResultsData.startingPrice.removeDecimal(),
                ),
            )
        }

    private fun setExpectedStockDetails(
        caseType: CaseType,
        stock: Stock? = null,
        stockResultsData: StockResultsData? = null,
    ) {
        when (caseType) {
            CaseType.Best -> {
                state = state.copy(
                    expectedResultsBest = getExpectedStockDetails(
                        stock = stock,
                        stockResultsData = stockResultsData,
                        caseType = caseType
                    )
                )
            }
            CaseType.Base -> {
                state = state.copy(
                    expectedResultsBase = getExpectedStockDetails(
                        stock = stock,
                        stockResultsData = stockResultsData,
                        caseType = caseType
                    )
                )
            }
            CaseType.Worst -> {
                state = state.copy(
                    expectedResultsWorst = getExpectedStockDetails(
                        stock = stock,
                        stockResultsData = stockResultsData,
                        caseType = caseType
                    )
                )
            }
        }
    }

    private fun getExpectedStockDetails(
        stock: Stock? = null,
        stockResultsData: StockResultsData? = null,
        caseType: CaseType = CaseType.Base
    ): ExpectedStockDetails {
        var expectedDetails = ExpectedStockDetails()

        if (stock != null && stockResultsData != null) {
            expectedDetails = stock.getExpectedDetails(stockResultsData = stockResultsData)
        } else {
            when (caseType) {
                CaseType.Worst -> state.selectedStock?.getExpectedDetails(state.stockResultsDataWorst)
                    ?.let { expectedDetails = it }

                CaseType.Base -> state.selectedStock?.getExpectedDetails(state.stockResultsDataBase)
                    ?.let { expectedDetails = it }

                CaseType.Best -> state.selectedStock?.getExpectedDetails(state.stockResultsDataBest)
                    ?.let { expectedDetails = it }
            }


        }
        return expectedDetails
    }
}


class StockResultsDataFields(
    @StringRes val title: Int,
    val onChange: (Double, CaseType) -> Unit,
    val defaultValue: String,
    val maxCharacters: Int = 100,
    val trailingIcon: Int? = null
)

fun Int.removeDecimal(): String = toInt().toString()
fun Double.removeDecimal(): String = toInt().toString()
package com.orels.presentation.ui.stock_price

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.orels.domain.model.entities.CaseType
import com.orels.domain.model.entities.ExpectedStockDetails
import com.orels.domain.model.entities.Stock
import com.orels.presentation.R
import com.orels.presentation.ui.components.Input

/**
 * @author Orel Zilberman
 * 06/10/2022
 */

@Composable
fun ResultsScreen(
    ticker: String,
    navController: NavController,
    viewModel: ResultsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = ticker) {
        viewModel.setStock(ticker)
    }

    val state = viewModel.state
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    } else {
        if (state.error != null) {
            Text(
                text = stringResource(state.error),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
        state.selectedStock?.let {
            Content(
                stock = it,
                expectedStockDetailsWorst = state.expectedResultsWorst,
                expectedStockDetailsBase = state.expectedResultsBase,
                expectedStockDetailsBest = state.expectedResultsBest,
                fields = state.stockResultsDataFields
            )
        }
    }
}


@Composable
fun Content(
    stock: Stock,
    expectedStockDetailsWorst: ExpectedStockDetails?,
    expectedStockDetailsBase: ExpectedStockDetails?,
    expectedStockDetailsBest: ExpectedStockDetails?,
    fields: Map<CaseType, List<StockResultsDataFields>>
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnPadding = 16.dp
    val columnWidth = screenWidth / 3 - columnPadding

    if (expectedStockDetailsWorst == null || expectedStockDetailsBase == null || expectedStockDetailsBest == null) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.error_no_stock_details),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                    .zIndex(2f)
                    .padding(top = 6.dp)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top)
            ) {
                Data(
                    title = StockText(
                        text = "${stock.name}(${stock.ticker})",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Data(
                    title = StockText(
                        text = stringResource(R.string.current_price_),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    ),
                    value = StockText(
                        text = "${stock.priceFmt ?: 0}$",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxSize()
                        .zIndex(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    fields.entries.forEach { entry ->
                        val expectedStockDetails = when (entry.key) {
                            CaseType.Worst -> expectedStockDetailsWorst
                            CaseType.Base -> expectedStockDetailsBase
                            CaseType.Best -> expectedStockDetailsBest
                        }
                        Column(
                            modifier = Modifier.width(columnWidth),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Data(
                                title = StockText(
                                    text = stringResource(R.string.buy_),
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                ),
                                value = StockText(
                                    text = expectedStockDetails.priceToBuyByEarningsFmt,
                                    color = if (expectedStockDetails.priceToBuyByEarnings >= (stock.price
                                            ?: 99999.0)
                                    ) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
                                ),
                                dataFontSize = DataFontSize.Small
                            )
                            Data(
                                title = StockText(
                                    text = stringResource(R.string.sell_),
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                ),
                                value = StockText(
                                    text = expectedStockDetails.priceToSellFmt,
                                    color = if (expectedStockDetails.priceToSellByEarnings >= (stock.price
                                            ?: 99999.toDouble())
                                    ) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
                                ),
                                dataFontSize = DataFontSize.Small
                            )
                            Data(
                                title = StockText(
                                    text = stringResource(R.string.irr_),
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                ),
                                value = StockText(
                                    text = expectedStockDetails.irrFmt,
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                dataFontSize = DataFontSize.Small
                            )
                            entry.value.forEach { field ->
                                Input(
                                    title = stringResource(id = field.title),
                                    placeholder = field.defaultValue,
                                    isPassword = false,
                                    keyboardOptions = KeyboardOptions(
                                        capitalization = KeyboardCapitalization.None,
                                        autoCorrect = true,
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    leadingIcon = {},
                                    trailingIcon = { tint ->
                                        field.trailingIcon?.let { painterResource(id = it) }?.let {
                                            Icon(
                                                painter = it,
                                                modifier = Modifier.size(24.dp),
                                                contentDescription = null,
                                                tint = tint
                                            )
                                        }
                                    },
                                    onTextChange = {
                                        field.onChange(it.toDoubleOrNull() ?: -1.0, entry.key)
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Data(
    title: StockText,
    modifier: Modifier = Modifier,
    value: StockText = StockText(color = MaterialTheme.colorScheme.onBackground),
    dataFontSize: DataFontSize = DataFontSize.Large
) {
    val typography = when (dataFontSize) {
        DataFontSize.Small -> MaterialTheme.typography.bodyLarge
        DataFontSize.Large -> MaterialTheme.typography.titleLarge
    }
    Row(modifier) {
        Text(
            text = title.text,
            style = typography,
            color = title.color
        )
        Text(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = value.text,
            style = typography,
            color = value.color
        )
    }
}

class StockText(
    val text: String = "",
    val color: Color
)

enum class DataFontSize {
    Small,
    Large
}
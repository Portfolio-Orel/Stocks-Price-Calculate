package com.orels.presentation.ui.stock_price

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
                expectedStockDetails = state.expectedResults,
                fields = state.stockResultsDataFields
            )
        }
    }
}


@Composable
fun Content(
    stock: Stock,
    expectedStockDetails: ExpectedStockDetails?,
    fields: List<StockResultsDataFields>
) {

    val lazyColumnState = rememberLazyListState()
    if (expectedStockDetails == null) {
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
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
                    .offset {
                        IntOffset(
                            0,
                            0
                        )
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Data(
                    title = StockText(
                        text = "${stock.name}(${stock.ticker})",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Data(
                    title = StockText(
                        text = stringResource(R.string.free_cashflow_yield),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    ),
                    value = StockText(
                        text = stock.getFreeCashflowYieldFmt() ?: "-",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Data(
                    title = StockText(
                        text = stringResource(R.string.price_to_buy),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    ),
                    value = StockText(
                        text = expectedStockDetails.priceToBuyByEarningsFmt,
                        color = if (expectedStockDetails.priceToBuyByEarnings >= (stock.price
                                ?: 99999.toDouble())
                        ) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
                    )
                )
                Data(
                    title = StockText(
                        text = stringResource(R.string.irr_),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    ),
                    value = StockText(
                        text = expectedStockDetails.irrFmt,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(1F)
                    .heightIn(max = 2000.dp),
                state = lazyColumnState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = fields.size,
                    key = { index ->
                        // Return a stable + unique key for the item
                        index
                    }
                ) { index ->
                    Input(
                        title = stringResource(id = fields[index].title),
                        placeholder = fields[index].defaultValue.toString(),
                        isPassword = false,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {},
                        trailingIcon = {},
                        onTextChange = {
                            fields[index].onChange(it.toDoubleOrNull() ?: -1.0)
                        },
                    )

                }
            }
        }
    }
}

@Composable
fun Data(
    title: StockText,
    value: StockText = StockText(color = MaterialTheme.colorScheme.onBackground),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(
            text = title.text,
            style = MaterialTheme.typography.titleLarge,
            color = title.color
        )
        Text(
            text = value.text,
            style = MaterialTheme.typography.titleLarge,
            color = value.color
        )
    }
}

class StockText(
    val text: String = "",
    val color: Color
)

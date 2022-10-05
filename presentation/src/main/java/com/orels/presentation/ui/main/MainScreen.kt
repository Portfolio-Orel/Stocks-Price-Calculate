package com.orels.presentation.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.orels.domain.model.entities.Stock
import com.orels.presentation.R

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state
    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .height(32.dp)
                .width(32.dp),
            strokeWidth = 2.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
    } else {
        if (state.error != null) {
            Text(
                text = stringResource(state.error),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
        state.selectedStock?.let { Content(it) }
    }
}

@Composable
fun Content(stock: Stock) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stock.ticker ?: stringResource(R.string.no_ticker),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(R.string.free_cashflow_yield) + stock.getFreeCashflowYieldFmt(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(R.string.price_to_buy) + stock.getExpectedDetails().priceToBuyByEarnings,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(R.string.price_to_sell) + stock.getExpectedDetails().priceToSell,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(R.string.irr_) + stock.getExpectedDetails().irr,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
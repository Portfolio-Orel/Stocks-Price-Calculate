package com.orels.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.orels.presentation.R
import com.orels.presentation.ui.components.Input
import com.orels.presentation.ui.components.TextStyle
import com.orels.presentation.util.Screen

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Input(
            modifier = Modifier.padding(12.dp),
            textStyle = TextStyle.AllCaps,
            title = stringResource(R.string.stock_ticker),
            placeholder = stringResource(R.string.meta_ticker),
            isPassword = false,
            leadingIcon = {},
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(
                        R.string.search_stock
                    )
                )
            },
            onTextChange = viewModel::onTickerChange
        )
        Button(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            onClick = {
                if(state.ticker.isNotEmpty()) {
                    navController.navigate(Screen.Results.withArgs(state.ticker))
                }
            }
        ) {
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}
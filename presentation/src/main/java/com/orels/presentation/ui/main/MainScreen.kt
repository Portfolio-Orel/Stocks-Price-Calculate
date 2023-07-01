package com.orels.presentation.ui.main

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val permissionState = remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                permissionState.value = true
            }
        }
    )

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
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (state.ticker.isNotEmpty()) {
                        navController.navigate(Screen.Results.withArgs(state.ticker))
                    }
                }
            ),
            leadingIcon = {},
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        if (state.ticker.isNotEmpty()) {
                            navController.navigate(Screen.Results.withArgs(state.ticker))
                        }
                    },
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(
                        R.string.search_stock
                    )
                )
            },
            onTextChange = viewModel::onTickerChange
        )
        FileExplorerButton(viewModel::onFileSelected)
    }
}

@Composable
fun FileExplorerButton(onFileSelected: (Uri, Context) -> Unit ) {
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { onFileSelected(it, context) }
        }
    )

    Button(onClick = { filePickerLauncher.launch("*/*") }) {
        Text("Open File Explorer")
    }
}

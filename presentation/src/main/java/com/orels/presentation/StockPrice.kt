package com.orels.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.orels.presentation.ui.components.CustomScaffold
import com.orels.presentation.ui.main.MainScreen
import com.orels.presentation.ui.stock_price.ResultsScreen
import com.orels.presentation.util.Screen

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StockPrice(
) {
    val navHostController = rememberNavController()
    val navController = navHostController as NavController

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        CustomScaffold(
            navController = navHostController,
//            topBar = { TopAppBar(navController = navController) },
//            bottomBar = { BottomBar(navController = it) },
        ) {
            NavHost(
                modifier = Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    end = it.calculateEndPadding(LayoutDirection.Rtl),
                    start = it.calculateStartPadding(LayoutDirection.Rtl)
                ),
                navController = navHostController, startDestination = Screen.Main.route
            ) {
                composable(route = Screen.Main.route) { MainScreen(navController = navHostController) }
                composable(route = Screen.Results.route + "/{ticker}",
                    arguments = listOf(
                        navArgument("ticker") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )) { navBackStack ->
                    ResultsScreen(
                        ticker = navBackStack.arguments?.getString("ticker") ?: "",
                        navController = navHostController,
                    )
                }
            }

        }
    }
}
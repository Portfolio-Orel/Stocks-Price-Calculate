package com.orels.presentation.ui.main

import androidx.annotation.StringRes
import com.orels.domain.model.entities.Stock

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
data class MainState(
    val selectedStock: Stock? = null,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)
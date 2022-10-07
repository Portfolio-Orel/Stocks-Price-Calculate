package com.orels.presentation.ui.main

import androidx.annotation.StringRes

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
data class MainState(
    val ticker: String = "",
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)
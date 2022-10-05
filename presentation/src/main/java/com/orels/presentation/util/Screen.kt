package com.orels.presentation.util

import androidx.annotation.StringRes
import com.orels.presentation.R

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Main : Screen(route = "main", resourceId = R.string.main)

    fun withArgs(vararg args: String?): String =
        buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg ?: ""}")
            }
        }
}
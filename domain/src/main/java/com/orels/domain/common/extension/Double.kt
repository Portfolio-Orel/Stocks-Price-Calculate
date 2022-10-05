package com.orels.domain.common.extension

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

fun Double.round(decimals: Int): Double =
    "%.${decimals}f".format(this).toDouble()

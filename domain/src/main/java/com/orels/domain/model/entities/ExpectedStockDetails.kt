package com.orels.domain.model.entities

import com.orels.domain.common.extension.round

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
data class ExpectedStockDetails(
    val currentPrice: Double,
    val irr: Double,
    val sharesOutstanding: Double,
    val priceToBuyByEarnings: Double,
    val priceToBuyByCashflow: Double,
    val priceToSell: Double,
) {
    val irrFmt: String
    get() = "${irr.round(2)}%"
}
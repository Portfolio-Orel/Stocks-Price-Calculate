package com.orels.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orels.domain.common.extension.round
import java.util.*

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@Entity
data class ExpectedStockDetails(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val currentPrice: Double = 0.0,
    val irr: Double = 0.0,
    val sharesOutstanding: Double = 0.0,
    val priceToBuyByEarnings: Double = 0.0,
    val priceToBuyByCashflow: Double = 0.0,
    val priceToSellByEarnings: Double = 0.0,
    val priceToSellByCashflow: Double = 0.0
) {

    val priceToBuyByEarningsFmt: String
        get() = "${priceToBuyByEarnings.round(2)}$"

    val priceToBuyByCashflowFmt: String
        get() = "${priceToBuyByCashflow.round(2)}$"

    val irrFmt: String
        get() = "${irr.round(2)}%"

    val priceToSellFmt: String
        get() = "${priceToSellByEarnings.round(1)}$"
}

class StockResultsData(
    stock: Stock?
) {
    var startingPrice: Double = 15.0
    var expectedPE: Double = 15.0
    var expectedPriceToFreeCashflow: Double = 15.0
    var expectedProfitMargin: Double = 15.0
    var expectedCashflowMargin: Double = 15.0
    var expectedAnnualGrowthRate: Double = 10.0
    var expectedSharesOutstanding: Double = 2E9
    var expectedSharesOutstandingReduction: Double = 0.0
    var expectedIRR: Double = 15.0
    var years: Double = 5.0

    init {
        startingPrice = stock?.price ?: 100.0
        expectedPE = stock?.trailingPE ?: stock?.forwardPe ?: 15.0
        expectedPriceToFreeCashflow = stock?.priceToFreeCashFlow ?: 15.0
        expectedProfitMargin = (stock?.profitMargin ?: 0.15) * 100
        expectedSharesOutstanding = stock?.sharesOutstanding ?: 2E9// 2B
    }
}
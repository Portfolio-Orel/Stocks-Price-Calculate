package com.orels.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orels.domain.common.extension.round
import java.util.UUID

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@Entity
data class ExpectedStockDetails(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val currentPrice: Double = 0.0,
    val irr: Double = 0.0,
    val sharesOutstanding: Int = 0,
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
        get() = "${irr.round(1)}%"

    val priceToSellFmt: String
        get() = "${priceToSellByEarnings.round(1)}$"
}

class StockResultsData(
    val stock: Stock?,
    caseType: CaseType = CaseType.Base
) {

    private val multiplier: Double = when (caseType) {
        CaseType.Worst -> 0.75
        CaseType.Base -> 1.0
        CaseType.Best -> 1.25
    }

    var startingPrice: Double = 15.0
    var expectedPE: Int = (15.0 * multiplier).toInt()
    var expectedPriceToFreeCashflow: Double = 15.0
    var expectedProfitMargin: Int = (15.0 * multiplier).toInt()
    var expectedCashflowMargin: Int = (15.0 * multiplier).toInt()
    var expectedAnnualGrowthRate: Int = (10.0 * multiplier).toInt()
    var expectedSharesOutstanding: Double = 2E9
    var expectedSharesOutstandingReduction: Int = 0
    var expectedIRR: Int = (15.0 * multiplier).toInt()
    var years: Int = 5


    init {
        startingPrice = stock?.price ?: 100.0
        expectedPE = ((stock?.trailingPE ?: stock?.forwardPe ?: 15.0) * multiplier).toInt()
        expectedPriceToFreeCashflow = stock?.priceToFreeCashFlow ?: 15.0
        expectedProfitMargin = (((stock?.profitMargin ?: 0.15) * 100) * multiplier).toInt()
        expectedSharesOutstanding = (stock?.sharesOutstanding ?: 2E9)
    }
}

fun StockResultsData.toWorstCase(): StockResultsData = StockResultsData(
    stock = stock
)

fun StockResultsData.toBestCase(): StockResultsData = this.apply {
    expectedPE = (expectedPE * 1.25).toInt()
    expectedPriceToFreeCashflow = (expectedPriceToFreeCashflow * 1.25)
    expectedProfitMargin = (expectedProfitMargin * 1.25).toInt()
    expectedSharesOutstanding *= 0.75
}

enum class CaseType {
    Worst,
    Base,
    Best
}
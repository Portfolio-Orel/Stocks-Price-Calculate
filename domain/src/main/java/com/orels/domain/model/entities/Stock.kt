package com.orels.domain.model.entities

import com.orels.domain.common.extension.round
import kotlin.math.pow

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class Stock(
    val ticker: String?,
    private val financialData: FinancialData,
    private val defaultKeyStatistics: DefaultKeyStatistics,
    private val summaryDetails: SummaryDetails
) {
    val forwardPe: Double?
        get() = defaultKeyStatistics.forwardPE.raw
    val forwardPeFmt: String?
        get() = defaultKeyStatistics.forwardPE.fmt

    val trailingPE: Double?
        get() = summaryDetails.trailingPE.raw
    val trailingPEFmt: String?
        get() = summaryDetails.trailingPE.fmt

    val price: Double?
        get() = financialData.currentPrice.raw
    val priceFmt: String?
        get() = financialData.currentPrice.fmt

    private fun getFreeCashflowYield(): Double? {
        val enterpriseValue = defaultKeyStatistics.enterpriseValue.raw
        val freeCashFlow = financialData.freeCashflow.raw
        if (enterpriseValue != null && freeCashFlow != null) {
            return 1 / (enterpriseValue / freeCashFlow)
        }
        return null
    }

    fun getFreeCashflowYieldFmt(): String? {
        val freeCashflowYield = getFreeCashflowYield()
        if (freeCashflowYield != null) {
            return "${(freeCashflowYield * 100).round(1)}%"
        }
        return null
    }

    fun getExpectedDetails(
        expectedPE: Double = 15.0,
        expectedPriceToFreeCashflow: Double = 15.0,
        expectedProfitMargin: Double = 15.0,
        expectedCashflowMargin: Double = 15.0,
        expectedAnnualGrowthRate: Double = 10.0,
        expectedSharesOutstandingReduction: Double = 0.0,
        expectedIRR: Double = 15.0,
        startingPrice: Double = financialData.currentPrice.raw ?: 1.0,
        years: Double = 5.0
    ): ExpectedStockDetails {
        val sharesOutstanding = defaultKeyStatistics.sharesOutstanding.raw
            ?: 0.0
        val currentRevenue = financialData.totalRevenue.raw ?: 0.0
        val newOutstandingShares =
            sharesOutstanding * (1 - expectedSharesOutstandingReduction / 100 / 1).pow(years)
        val newFutureRevenue =
            currentRevenue * ((1 + expectedAnnualGrowthRate / 100).pow(years))
        val newFutureEarnings = newFutureRevenue * (expectedProfitMargin / 100)
        val newFutureEPS = newFutureEarnings / newOutstandingShares
        val newFuturePrice: Double = if (newFutureEPS < 0 || expectedPE < 0) {
            0.0
        } else {
            newFutureEPS * expectedPE
        }
        val newIRR = (newFuturePrice / startingPrice).pow(1 / years) - 1
        val newFutureCashflow = newFutureRevenue * (expectedCashflowMargin / 100)
        val newCashflowPerShare = newFutureCashflow / sharesOutstanding
        val priceToBuyByCashflow = newCashflowPerShare * expectedPriceToFreeCashflow
        val priceToBuyByEarnings = newFuturePrice / (expectedIRR / 100 + 1).pow(years)
        val priceToSell = newFuturePrice / (12.5 / 100 + 1).pow(years)
        return ExpectedStockDetails(
            currentPrice = financialData.currentPrice.raw ?: 0.0,
            irr = newIRR,
            sharesOutstanding = newOutstandingShares,
            priceToBuyByEarnings = priceToBuyByEarnings,
            priceToBuyByCashflow = priceToBuyByCashflow,
            priceToSell = priceToSell
        )
    }
}
package com.orels.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orels.domain.common.extension.round
import kotlin.math.pow

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@Entity
class Stock(
    @PrimaryKey val ticker: String,
    val name: String?,
    val financialData: FinancialData,
    val defaultKeyStatistics: DefaultKeyStatistics,
    val summaryDetails: SummaryDetails,
    val calendarEvents: CalendarEvents,
    val savedInCacheTime: Long = System.currentTimeMillis(),
) {
    val forwardPe: Double?
        get() = defaultKeyStatistics.forwardPE.raw
    val forwardPeFmt: String?
        get() = defaultKeyStatistics.forwardPE.fmt

    val trailingPE: Double?
        get() = summaryDetails.trailingPE.raw
    val trailingPEFmt: String?
        get() = summaryDetails.trailingPE.fmt

    val profitMargin: Double?
        get() = financialData.profitMargins.raw
    val profitMarginFmt: String?
        get() = financialData.profitMargins.fmt

    val sharesOutstanding: Double?
        get() = defaultKeyStatistics.sharesOutstanding.raw
    val sharesOutstandingFmt: String?
        get() = defaultKeyStatistics.sharesOutstanding.fmt

    val price: Double?
        get() = financialData.currentPrice.raw
    val priceFmt: String?
        get() = financialData.currentPrice.fmt

    val priceToFreeCashFlow: Double
        get() = (summaryDetails.marketCap.raw ?: -1.0) / (financialData.freeCashflow.raw ?: 1.0)

    private fun getFreeCashflowYield(): Double? {
        val enterpriseValue = defaultKeyStatistics.enterpriseValue.raw
        val freeCashFlow = financialData.freeCashflow.raw
        if (enterpriseValue != null && freeCashFlow != null) {
            return 1 / (enterpriseValue / freeCashFlow)
        }
        return null
    }

    fun isExpired(): Boolean =
        calendarEvents.earnings.earningsDate.lastOrNull()?.raw?.let { lastEarnings ->
            return lastEarnings > savedInCacheTime
        } ?: false



    fun getFreeCashflowYieldFmt(): String? {
        val freeCashflowYield = getFreeCashflowYield()
        if (freeCashflowYield != null) {
            return "${(freeCashflowYield * 100).round(1)}%"
        }
        return null
    }

    fun getExpectedDetails(
        stockResultsData: StockResultsData?,
    ): ExpectedStockDetails? {
        if (stockResultsData == null) return null
        with(stockResultsData) {
            val sharesOutstanding = stockResultsData.expectedSharesOutstanding
                ?: defaultKeyStatistics.sharesOutstanding.raw
                ?: 0.0
            val currentRevenue = financialData.totalRevenue.raw ?: 0.0
            val newOutstandingShares =
                sharesOutstanding * (1 - expectedSharesOutstandingReduction / 100 / 1).pow(years)

            // Earnings
            val newFutureRevenue =
                currentRevenue * ((1 + expectedAnnualGrowthRate / 100).pow(years))
            val newFutureEarnings = newFutureRevenue * (expectedProfitMargin / 100)
            val newFutureEPS = newFutureEarnings / newOutstandingShares
            val newFuturePrice: Double = if (newFutureEPS < 0 || expectedPE < 0) {
                0.0
            } else {
                newFutureEPS * expectedPE
            }
            val newIRR = ((newFuturePrice / startingPrice).pow(1 / years) - 1) * 100
            val priceToBuyByEarnings = newFuturePrice / (expectedIRR / 100 + 1).pow(years)
            val priceToSell = newFuturePrice / (12.5 / 100 + 1).pow(years)

            // Cashflow
            val newFutureCashflow = newFutureRevenue * (expectedCashflowMargin / 100)
            val newCashflowPerShare = newFutureCashflow / sharesOutstanding
            val newFuturePriceByCashflow = newCashflowPerShare * expectedPriceToFreeCashflow
            val newIRRByCashflow =
                ((newFuturePriceByCashflow / startingPrice).pow(1 / years) - 1) * 100
            val priceToBuyByCashflow =
                newFuturePriceByCashflow / (newIRRByCashflow / 100 + 1).pow(years)
            val priceToSellByCashflow =
                newFuturePriceByCashflow / (expectedIRR / 100 + 1).pow(years)

            return ExpectedStockDetails(
                currentPrice = financialData.currentPrice.raw ?: 0.0,
                irr = newIRR,
                sharesOutstanding = newOutstandingShares,
                priceToBuyByEarnings = priceToBuyByEarnings,
                priceToBuyByCashflow = priceToBuyByCashflow,
                priceToSellByEarnings = priceToSell,
                priceToSellByCashflow = priceToSellByCashflow
            )
        }
    }
}
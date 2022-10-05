package com.orels.domain.model.entities

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class FinancialData(
    val ebitdaMargins: Base? = null,
    val profitMargins: Base? = null,
    val grossMargins: Base? = null,
    val operatingCashflow: Base? = null,
    val operatingMargins: Base? = null,
    val ebitda: Base? = null,
    val grossProfits: Base? = null,
    val freeCashflow: Base? = null,
    val targetMedianPrice: Base? = null,
    val currentPrice: Base? = null,
    val returnOnAssets: Base? = null,
    val targetMeanPrice: Base? = null,
    val debtToEquity: Base? = null,
    val returnOnEquity: Base? = null,
    val targetHighPrice: Base? = null,
    val totalCash: Base? = null,
    val totalDebt: Base? = null,
    val totalRevenue: Base? = null,
    val totalCashPerShare: Base? = null,
    val financialCurrency: String? = null,
)
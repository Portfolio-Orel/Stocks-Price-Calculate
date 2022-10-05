package com.orels.domain.model.entities

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class FinancialData(
    val ebitdaMargins: Base,
    val profitMargins: Base,
    val grossMargins: Base,
    val operatingCashflow: Base,
    val operatingMargins: Base,
    val ebitda: Base,
    val grossProfits: Base,
    val freeCashflow: Base,
    val targetMedianPrice: Base,
    val currentPrice: Base,
    val returnOnAssets: Base,
    val targetMeanPrice: Base,
    val debtToEquity: Base,
    val returnOnEquity: Base,
    val targetHighPrice: Base,
    val totalCash: Base,
    val totalDebt: Base,
    val totalRevenue: Base,
    val totalCashPerShare: Base,
    val financialCurrency: String? = null,
)
package com.orels.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
@Entity
class FinancialData(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val ebitdaMargins: Base = Base(),
    val profitMargins: Base = Base(),
    val grossMargins: Base = Base(),
    val operatingCashflow: Base = Base(),
    val operatingMargins: Base = Base(),
    val ebitda: Base = Base(),
    val grossProfits: Base = Base(),
    val freeCashflow: Base = Base(),
    val targetMedianPrice: Base = Base(),
    val currentPrice: Base = Base(),
    val returnOnAssets: Base = Base(),
    val targetMeanPrice: Base = Base(),
    val debtToEquity: Base = Base(),
    val returnOnEquity: Base = Base(),
    val targetHighPrice: Base = Base(),
    val totalCash: Base = Base(),
    val totalDebt: Base = Base(),
    val totalRevenue: Base = Base(),
    val totalCashPerShare: Base = Base(),
    val financialCurrency: String? = "Non",
)
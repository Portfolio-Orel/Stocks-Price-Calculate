package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class StockResponse(
    @SerializedName("financialData") val financialData: FinancialData? = null,
    @SerializedName("defaultKeyStatistics") val defaultKeyStatistics: DefaultKeyStatistics? = null,
    @SerializedName("summaryDetails") val summaryDetails: SummaryDetails? = null,
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("quoteType") val quoteType: QuoteType
)
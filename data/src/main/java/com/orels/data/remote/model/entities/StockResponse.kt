package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class StockResponse(
    @SerializedName("financialData") val financialData: FinancialData,
    @SerializedName("defaultKeyStatistics") val defaultKeyStatistics: DefaultKeyStatistics,
    @SerializedName("summaryDetails") val summaryDetails: SummaryDetails
)
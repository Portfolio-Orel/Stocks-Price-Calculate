package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class FinancialData(
    @SerializedName("ebitdaMargins")
    val ebitdaMargins: BaseResponse,
    @SerializedName("profitMargins")
    val profitMargins: BaseResponse,
    @SerializedName("grossMargins")
    val grossMargins: BaseResponse,
    @SerializedName("operatingCashflow")
    val operatingCashflow: BaseResponse,
    @SerializedName("operatingMargins")
    val operatingMargins: BaseResponse,
    @SerializedName("ebitda")
    val ebitda: BaseResponse,
    @SerializedName("grossProfits")
    val grossProfits: BaseResponse,
    @SerializedName("freeCashflow")
    val freeCashflow: BaseResponse,
    @SerializedName("targetMedianPrice")
    val targetMedianPrice: BaseResponse,
    @SerializedName("currentPrice")
    val currentPrice: BaseResponse,
    @SerializedName("returnOnAssets")
    val returnOnAssets: BaseResponse,
    @SerializedName("numberOfAnalystOpinions")
    val targetMeanPrice: BaseResponse,
    @SerializedName("debtToEquity")
    val debtToEquity: BaseResponse,
    @SerializedName("returnOnEquity")
    val returnOnEquity: BaseResponse,
    @SerializedName("targetHighPrice")
    val targetHighPrice: BaseResponse,
    @SerializedName("totalCash")
    val totalCash: BaseResponse,
    @SerializedName("totalDebt")
    val totalDebt: BaseResponse,
    @SerializedName("totalRevenue")
    val totalRevenue: BaseResponse,
    @SerializedName("totalCashPerShare")
    val totalCashPerShare: BaseResponse,
    @SerializedName("financialCurrency")
    val financialCurrency: String? = null,
)
package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class FinancialData(
    @SerializedName("ebitdaMargins")
    val ebitdaMargins: BaseResponse? = null,
    @SerializedName("profitMargins")
    val profitMargins: BaseResponse? = null,
    @SerializedName("grossMargins")
    val grossMargins: BaseResponse? = null,
    @SerializedName("operatingCashflow")
    val operatingCashflow: BaseResponse? = null,
    @SerializedName("operatingMargins")
    val operatingMargins: BaseResponse? = null,
    @SerializedName("ebitda")
    val ebitda: BaseResponse? = null,
    @SerializedName("grossProfits")
    val grossProfits: BaseResponse? = null,
    @SerializedName("freeCashflow")
    val freeCashflow: BaseResponse? = null,
    @SerializedName("targetMedianPrice")
    val targetMedianPrice: BaseResponse? = null,
    @SerializedName("currentPrice")
    val currentPrice: BaseResponse? = null,
    @SerializedName("returnOnAssets")
    val returnOnAssets: BaseResponse? = null,
    @SerializedName("numberOfAnalystOpinions")
    val targetMeanPrice: BaseResponse? = null,
    @SerializedName("debtToEquity")
    val debtToEquity: BaseResponse? = null,
    @SerializedName("returnOnEquity")
    val returnOnEquity: BaseResponse? = null,
    @SerializedName("targetHighPrice")
    val targetHighPrice: BaseResponse? = null,
    @SerializedName("totalCash")
    val totalCash: BaseResponse? = null,
    @SerializedName("totalDebt")
    val totalDebt: BaseResponse? = null,
    @SerializedName("totalRevenue")
    val totalRevenue: BaseResponse? = null,
    @SerializedName("totalCashPerShare")
    val totalCashPerShare: BaseResponse? = null,
    @SerializedName("financialCurrency")
    val financialCurrency: String? = null,
)
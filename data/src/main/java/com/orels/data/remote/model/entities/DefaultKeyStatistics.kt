package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class DefaultKeyStatistics (
    @SerializedName("enterpriseToRevenue") val enterpriseToRevenue: BaseResponse,
    @SerializedName("sharesOutstanding") val sharesOutstanding: BaseResponse,
    @SerializedName("sharesShort") val sharesShort: BaseResponse,
    @SerializedName("trailingEps") val trailingEps: BaseResponse,
    @SerializedName("heldPercentInsiders") val heldPercentInsiders: BaseResponse,
    @SerializedName("beta") val beta: BaseResponse,
    @SerializedName("enterpriseValue") val enterpriseValue: BaseResponse,
    @SerializedName("forwardPE") val forwardPE: BaseResponse,
    @SerializedName("impliedSharesOutstanding") val impliedSharesOutstanding: BaseResponse,
    @SerializedName("priceToBook") val priceToBook: BaseResponse,
) {

}
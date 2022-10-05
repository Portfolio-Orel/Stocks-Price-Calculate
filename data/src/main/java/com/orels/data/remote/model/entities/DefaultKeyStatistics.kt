package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class DefaultKeyStatistics (
    @SerializedName("enterpriseToRevenue") val enterpriseToRevenue: BaseResponse? = null,
    @SerializedName("sharesOutstanding") val sharesOutstanding: BaseResponse? = null,
    @SerializedName("sharesShort") val sharesShort: BaseResponse? = null,
    @SerializedName("trailingEps") val trailingEps: BaseResponse? = null,
    @SerializedName("heldPercentInsiders") val heldPercentInsiders: BaseResponse? = null,
    @SerializedName("beta") val beta: BaseResponse? = null,
    @SerializedName("enterpriseValue") val enterpriseValue: BaseResponse? = null,
    @SerializedName("forwardPE") val forwardPE: BaseResponse? = null,
    @SerializedName("impliedSharesOutstanding") val impliedSharesOutstanding: BaseResponse? = null,
    @SerializedName("priceToBook") val priceToBook: BaseResponse? = null,
) {

}
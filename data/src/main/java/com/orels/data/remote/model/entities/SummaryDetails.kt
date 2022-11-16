package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class SummaryDetails(
    @SerializedName("trailingPE") val trailingPE: BaseResponse? = null,
    @SerializedName("forwardPE") val forwardPE: BaseResponse,
    @SerializedName("marketCap") val marketCap: BaseResponse,
)
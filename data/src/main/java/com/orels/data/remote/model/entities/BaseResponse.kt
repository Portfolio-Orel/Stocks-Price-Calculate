package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
abstract class BaseResponse(
    @SerializedName("raw") val raw: Double? = null,
    @SerializedName("fmt") val fmt: String? = null
)
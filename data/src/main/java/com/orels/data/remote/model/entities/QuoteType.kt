package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
data class QuoteType(
    @SerializedName("longName") var longName: String? = null,
    @SerializedName("shortName") var shortName: String? = null
)
package com.orels.data.remote.model.entities

import com.google.gson.annotations.SerializedName
import com.orels.domain.model.entities.Base

data class CalendarEvents(
    @SerializedName("earnings") val earnings: Earnings = Earnings(),
)

data class Earnings(
    @SerializedName("earningsDate") val earningsDate: List<Base> = listOf(),
)
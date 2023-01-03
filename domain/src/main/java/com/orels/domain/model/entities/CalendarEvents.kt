package com.orels.domain.model.entities

data class CalendarEvents(
    val earnings: Earnings = Earnings(),
)

data class Earnings(
    val earningsDate: List<Base> = listOf(),
)
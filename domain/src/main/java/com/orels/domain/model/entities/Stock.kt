package com.orels.domain.model.entities

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class Stock(
    private val financialData: FinancialData,
    private val defaultKeyStatistics: DefaultKeyStatistics,
    private val summaryDetails: SummaryDetails
)
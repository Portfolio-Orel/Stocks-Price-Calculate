package com.orels.domain.model.entities

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
class DefaultKeyStatistics (
    val enterpriseToRevenue: Base = Base(),
    val sharesOutstanding: Base = Base(),
    val sharesShort: Base = Base(),
    val trailingEps: Base = Base(),
    val heldPercentInsiders: Base = Base(),
    val beta: Base = Base(),
    val enterpriseValue: Base = Base(),
    val forwardPE: Base = Base(),
    val impliedSharesOutstanding: Base = Base(),
    val priceToBook: Base = Base()
) {

}
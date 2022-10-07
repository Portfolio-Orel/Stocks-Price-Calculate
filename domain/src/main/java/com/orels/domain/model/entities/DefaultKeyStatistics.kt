package com.orels.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@Entity
class DefaultKeyStatistics (
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
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
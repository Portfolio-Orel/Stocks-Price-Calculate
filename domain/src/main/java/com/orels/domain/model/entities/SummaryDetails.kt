package com.orels.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@Entity
class SummaryDetails(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val trailingPE: Base = Base(),
    val forwardPE: Base = Base(),
    val marketCap: Base = Base()
)
package com.orels.domain.model.interactors

import com.orels.domain.model.entities.Stock

/**
 * @author Orel Zilberman
 * 05/10/2022
 */
interface Repository {
    suspend fun getStock(ticker: String): Stock?
}
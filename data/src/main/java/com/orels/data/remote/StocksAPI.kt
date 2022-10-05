package com.orels.data.remote

import com.orels.data.remote.model.entities.StockResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

interface StocksAPI {
    @GET("stock/v3/get-statistics")
    suspend fun getStockDetails(@Query("symbol") ticker: String): StockResponse
}
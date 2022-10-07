package com.orels.data.local

import androidx.room.*
import com.orels.domain.model.entities.Stock

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stock: Stock)

    @Delete
    fun delete(stock: Stock)

    @Query(
        """
        SELECT *
        FROM Stock
        Where ticker = :ticker
    """
    )
    fun get(ticker: String): Stock?
}
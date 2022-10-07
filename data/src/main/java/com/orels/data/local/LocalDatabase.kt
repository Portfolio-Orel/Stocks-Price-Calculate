package com.orels.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.orels.data.local.type_converters.Converters
import com.orels.domain.model.entities.Stock

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@Database(
    entities = [
        Stock::class
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract val stockDao: StockDao
}
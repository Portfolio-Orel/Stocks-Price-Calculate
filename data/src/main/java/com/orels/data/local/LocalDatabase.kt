package com.orels.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.orels.data.local.type_converters.Converters

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@Database(entities = [], version = 0)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

}
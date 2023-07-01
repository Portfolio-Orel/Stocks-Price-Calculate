package com.orels.stock_price

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@HiltAndroidApp
class StockPriceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
package com.orels.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.orels.domain.model.entities.Stock
import com.orels.domain.model.interactors.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var state by mutableStateOf(MainState())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val stock: Stock = repository.getStock("AMZN")
                println(stock.summaryDetails)
            } catch(e: Exception) {
                print("e: $e")
            }
        }
    }
}
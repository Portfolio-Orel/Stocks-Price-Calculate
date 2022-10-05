package com.orels.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Orel Zilberman
 * 05/10/2022
 */

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    var state by mutableStateOf(MainState())
}
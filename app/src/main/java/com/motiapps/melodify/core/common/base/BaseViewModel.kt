package com.motiapps.melodify.core.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

//    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
//    val isLoading = _isLoading.asStateFlow()
//
//    fun setLoading(isLoading: Boolean) {
//        _isLoading.value = isLoading
//    }
}
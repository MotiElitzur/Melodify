package com.motiapps.melodify.core.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motiapps.melodify.core.data.repository.base.DataRepository
import com.motiapps.melodify.core.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val dataRepository: DataRepository,
) : ViewModel() {

    val user: StateFlow<User?> = dataRepository.user

    init {
        viewModelScope.launch {
            dataRepository.loadUserData()
        }
    }
}
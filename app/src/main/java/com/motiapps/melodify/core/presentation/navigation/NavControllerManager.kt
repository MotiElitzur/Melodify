package com.motiapps.melodify.core.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavControllerManager {

    private val _currentDestination = MutableStateFlow<NavDestination?>(null)
    val currentDestination: StateFlow<NavDestination?> = _currentDestination

    fun setupWithNavController(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            _currentDestination.value = destination
        }
    }
}
package motiapps.melodify.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import motiapps.melodify.common.datastore.data.model.PreferenceObject
import motiapps.melodify.common.datastore.domain.usecase.PreferencesUseCases
import motiapps.melodify.common.permissions.domain.usecase.CheckPermissionUseCase
import motiapps.melodify.common.permissions.domain.usecase.PermissionsUseCases
import motiapps.melodify.core.domain.base.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pref: PreferencesUseCases,
    private val permissionUseCases: PermissionsUseCases
) : ViewModel() {

    init {

        pref.observePreferenceUseCase(PreferenceObject("key", "default value")).onEach {
            when (it) {
                is Resource.Success -> {
                    val value = it.data
                    println("observe value = $value")
                }

                is Resource.Error -> {
                    val exception = it.exception
                    println("observe exception = $exception")
                }
            }
        }.launchIn(viewModelScope)

        viewModelScope.launch {

            when(val result = permissionUseCases.checkPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
                is Resource.Success -> {
                    val value = result.data
                    println("value = $value")
                }
                is Resource.Error -> {
                    val exception = result.exception
                    println("exception = $exception")
                }
            }

            when(val result = permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
                is Resource.Success -> {
                    val value = result.data
                    println("value = $value")
                }
                is Resource.Error -> {
                    val exception = result.exception
                    println("exception = $exception")
                }
            }

            when(val result = permissionUseCases.checkPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
                is Resource.Success -> {
                    val value = result.data
                    println("value = $value")
                }
                is Resource.Error -> {
                    val exception = result.exception
                    println("exception = $exception")
                }
            }

            when(val result = permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
                is Resource.Success -> {
                    val value = result.data
                    println("value = $value")
                }
                is Resource.Error -> {
                    val exception = result.exception
                    println("exception = $exception")
                }
            }

            when(val result = permissionUseCases.checkPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
                is Resource.Success -> {
                    val value = result.data
                    println("value = $value")
                }
                is Resource.Error -> {
                    val exception = result.exception
                    println("exception = $exception")
                }
            }

            when(val result = permissionUseCases.requestPermissionUseCase(android.Manifest.permission.POST_NOTIFICATIONS)) {
                is Resource.Success -> {
                    val value = result.data
                    println("value = $value")
                }
                is Resource.Error -> {
                    val exception = result.exception
                    println("exception = $exception")
                }
            }
        }
    }
}
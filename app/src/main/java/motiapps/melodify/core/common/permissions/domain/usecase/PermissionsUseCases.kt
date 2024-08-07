package motiapps.melodify.core.common.permissions.domain.usecase

import javax.inject.Inject

data class PermissionsUseCases @Inject constructor(
    val checkPermissionUseCase: CheckPermissionUseCase,
    val requestPermissionUseCase: RequestPermissionUseCase
)
package melodify.permission.domain.usecase

import javax.inject.Inject

data class PermissionsUseCases @Inject constructor(
    val checkPermissionUseCase: CheckPermissionUseCase,
    val requestPermissionUseCase: RequestPermissionUseCase
)
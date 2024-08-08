package motiapps.melodify.common.permissions.domain.usecase

import motiapps.melodify.common.permissions.domain.repository.PermissionRepository
import motiapps.melodify.common.permissions.domain.model.PermissionResult
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import javax.inject.Inject

class RequestPermissionUseCase @Inject constructor(
    private val permissionRepository: PermissionRepository
): SuspendUseCase<String, Resource<PermissionResult>>() {

    override suspend fun execute(params: String?): Resource<PermissionResult> {
        return try {
            if (params == null) {
                Resource.Error(Exception("Permission is null"))
            } else {
                Resource.Success(permissionRepository.requestPermission(params))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
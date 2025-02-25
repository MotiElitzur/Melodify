package melodify.permission.domain.usecase

import melodify.core.domain.Resource
import melodify.core.domain.SuspendUseCase
import melodify.permission.domain.repository.PermissionRepository
import melodify.permission.domain.model.PermissionResult
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
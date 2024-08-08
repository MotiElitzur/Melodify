package motiapps.melodify.common.user.domain.source

import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.common.user.data.model.UserDto

interface UserRemoteDataSource {

    suspend fun getUserById(userId: String): Resource<UserDto>

    suspend fun getUser(): Resource<UserDto>

    suspend fun insertUser(userId: String, user: UserDto): Resource<Unit>

    suspend fun updateUser(userId: String, dataToUpdate: Map<String, Any>): Resource<Unit>

    suspend fun deleteUser(userId: String): Resource<Unit>
}
package motiapps.melodify.common.user.domain.repository

import motiapps.melodify.common.user.data.model.User
import melodify.core.domain.Resource

interface UserRepository {

    suspend fun getUserById(userId: String): Resource<User>

    suspend fun fetchUser(userId: String, returnLocalOnError: Boolean? = false): Resource<User>

    suspend fun insertUser(user: User): Resource<Unit>

    suspend fun updateUser(userId: String, dataToUpdate: Map<String, Any>): Resource<Unit>

    suspend fun deleteUser(userId: String): Resource<Unit>
}
package motiapps.melodify.core.domain.repository

import motiapps.melodify.core.data.model.User
import motiapps.melodify.core.domain.base.Resource

interface UserRepository {

    suspend fun getUserById(userId: String): Resource<User>

    suspend fun fetchUser(userId: String, returnLocalOnError: Boolean? = false): Resource<User>

    suspend fun insertUser(user: User): Resource<Unit>

    suspend fun deleteUser(userId: String): Resource<Unit>
}
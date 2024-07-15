package motiapps.melodify.core.domain.repository

import motiapps.melodify.core.domain.model.User

interface UserRepository {

    suspend fun getUserById(userId: String): User?

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)
}
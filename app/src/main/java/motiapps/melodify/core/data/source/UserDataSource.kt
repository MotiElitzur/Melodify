package motiapps.melodify.core.data.source

import motiapps.melodify.core.domain.model.User

interface UserDataSource {

    suspend fun getUserById(userId: String): User?

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)
}
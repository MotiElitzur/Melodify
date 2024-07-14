package com.motiapps.melodify.core.data.source

import com.motiapps.melodify.core.domain.model.User

interface UserDataSource {

    suspend fun getUserById(userId: String): User?

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)
}
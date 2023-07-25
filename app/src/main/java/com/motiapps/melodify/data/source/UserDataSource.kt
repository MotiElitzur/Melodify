package com.motiapps.melodify.data.source

import com.motiapps.melodify.domain.model.User

interface UserDataSource {

    suspend fun getUserById(userId: String): User?

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)
}
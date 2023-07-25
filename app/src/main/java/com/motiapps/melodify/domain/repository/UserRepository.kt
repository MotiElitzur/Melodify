package com.motiapps.melodify.domain.repository

import com.motiapps.melodify.domain.model.User

interface UserRepository {

    suspend fun getUserById(userId: String): User?

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)
}
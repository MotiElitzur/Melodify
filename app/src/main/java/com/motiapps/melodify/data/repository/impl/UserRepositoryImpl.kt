package com.motiapps.melodify.data.repository.impl

import com.motiapps.melodify.data.source.UserDao
import com.motiapps.melodify.domain.model.User
import com.motiapps.melodify.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDao
) : UserRepository {

    override suspend fun getUserById(userId: String): User? {

        return userDataSource.getUserById(userId)
    }

    override suspend fun insertUser(user: User) {
        userDataSource.insertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        userDataSource.deleteUser(user)
    }
}
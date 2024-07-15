package motiapps.melodify.core.data.repository.impl

import motiapps.melodify.core.data.source.UserDao
import motiapps.melodify.core.domain.model.User
import motiapps.melodify.core.domain.repository.UserRepository
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
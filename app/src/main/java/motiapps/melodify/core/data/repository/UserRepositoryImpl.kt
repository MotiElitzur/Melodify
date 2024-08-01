package motiapps.melodify.core.data.repository

import motiapps.melodify.core.data.model.User
import motiapps.melodify.core.data.model.toDto
import motiapps.melodify.core.data.source.local.UserDao
import motiapps.melodify.core.data.source.remote.FirebaseDataSource
import motiapps.melodify.core.data.source.remote.FirebaseDataSourceImpl
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.model.toUser
import motiapps.melodify.core.domain.repository.UserRepository
import motiapps.melodify.core.presentation.base.error.BaseErrorType
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserDao,
    private val userRemoteDataSource: FirebaseDataSource
) : UserRepository {

    private var cachedUser: User? = null

    override suspend fun getUserById(userId: String): Resource<User> {
        // Check if the cached user is available and matches the requested user ID
        if (cachedUser != null && cachedUser!!.id == userId) {
            return Resource.Success(cachedUser!!)
        }

        return fetchUser(userId, true)
    }

    override suspend fun fetchUser(userId: String, returnLocalOnError: Boolean?): Resource<User> {
        // Try to fetch from remote data source
        return when (val remoteUserResult = userRemoteDataSource.getUserById(userId)) {
            is Resource.Success -> {
                // Save the remote data to the local database and update cache
                userLocalDataSource.insertUser(remoteUserResult.data)
                cachedUser = remoteUserResult.data.toUser()
                Resource.Success(cachedUser!!)
            }
            is Resource.Error -> {

                if (returnLocalOnError == true) {
                    // Fetch from local data source if remote fetch fails
                    val localUser = userLocalDataSource.getUserById(userId)
                    if (localUser != null) {
                        cachedUser = localUser.toUser()
                        Resource.Success(cachedUser!!, BaseErrorType.Network)
                    } else {
                        Resource.Error(remoteUserResult.exception)
                    }
                } else {
                    remoteUserResult
                }
            }
        }
    }

    override suspend fun insertUser(user: User): Resource<Unit> {
        return try {
            // Insert into both local and remote data sources
            userLocalDataSource.insertUser(user.toDto())
            val result = userRemoteDataSource.insertUser(user.id, user.toDto())
            if (result is Resource.Success) {
                cachedUser = user
            }
            result
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun deleteUser(userId: String): Resource<Unit> {
        return try {
            userLocalDataSource.deleteUser(userId)
            val result = userRemoteDataSource.deleteUser(userId)
            if (result is Resource.Success) {
                cachedUser = null
            }
            result
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
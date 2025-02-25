package motiapps.melodify.common.user.data.repository

import motiapps.melodify.common.user.data.source.local.UserDao
import motiapps.melodify.common.user.data.model.User
import motiapps.melodify.common.user.data.model.toDto
import motiapps.melodify.common.user.data.model.update
import motiapps.melodify.common.firebase.user.domain.repository.UserRemoteDataSource
import melodify.core.domain.Resource
import motiapps.melodify.common.user.data.model.toUser
import motiapps.melodify.common.user.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserDao,
    private val userRemoteDataSource: UserRemoteDataSource
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
                        Resource.Success(cachedUser!!)
                    } else {
                        Resource.Error(remoteUserResult.exception)
                    }
                } else {
                    remoteUserResult
                }
            }
            is Resource.Loading -> Resource.Loading
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

    override suspend fun updateUser(
        userId: String,
        dataToUpdate: Map<String, Any>
    ): Resource<Unit> {
        return try {
            // Update the local data source
            userLocalDataSource.updateUserFields(userId, dataToUpdate)
            val result = userRemoteDataSource.updateUser(userId, dataToUpdate)
            if (result is Resource.Success) {
                // Update the cached user if the remote update is successful
                cachedUser?.update(dataToUpdate)
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
package motiapps.melodify.common.firebase.user.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import motiapps.melodify.common.firebase.domain.FirestoreProvider
import melodify.core.domain.Resource
import motiapps.melodify.common.user.data.model.UserDto
import motiapps.melodify.common.firebase.user.domain.repository.UserRemoteDataSource
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestoreProvider: FirestoreProvider
) : UserRemoteDataSource {

    override suspend fun getUserById(userId: String): Resource<UserDto> {
        return try {
            val document = firestoreProvider.getUserReference(userId).get().await()
            val user = document.toObject(UserDto::class.java)
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error(Exception("getUserById User not found with id: $userId"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getUser(): Resource<UserDto> {
        return if (firebaseAuth.currentUser?.uid != null) {
            getUserById(firebaseAuth.currentUser!!.uid)
        } else {
            Resource.Error(Exception("User Auth not found"))
        }
    }

    override suspend fun insertUser(userId: String, user: UserDto): Resource<Unit> {
        return try {
            firestoreProvider.getUserReference(userId).set(user).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun updateUser(userId: String, dataToUpdate: Map<String, Any>): Resource<Unit> {
        return try {
            firestoreProvider.getUserReference(userId).update(dataToUpdate).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun deleteUser(userId: String): Resource<Unit> {
        return try {
            firestoreProvider.getUserReference(userId).delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
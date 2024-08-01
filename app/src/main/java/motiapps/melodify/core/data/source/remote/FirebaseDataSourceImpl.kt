package motiapps.melodify.core.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.model.UserDto
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseDataSource {

    override suspend fun getUserById(userId: String): Resource<UserDto> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            val user = document.toObject(UserDto::class.java)
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error(Exception("User not found"))
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
            firestore.collection("users").document().set(user).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun deleteUser(userId: String): Resource<Unit> {
        return try {
            firestore.collection("users").document(userId).delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
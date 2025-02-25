package motiapps.melodify.common.firebase.user.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import melodify.core.domain.Resource
import motiapps.melodify.common.firebase.user.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun getAuthUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun getAuthUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun isUserAuthLoggedIn(): Resource<Boolean> {
        return try {
            Resource.Success(firebaseAuth.currentUser != null)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun logout(): Resource<Boolean> {
        return try {
            firebaseAuth.signOut()
            Resource.Success(true)
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}
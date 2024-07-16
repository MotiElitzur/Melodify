package motiapps.melodify.core.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import motiapps.melodify.core.domain.repository.AuthRepository
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

    override suspend fun isUserAuthLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
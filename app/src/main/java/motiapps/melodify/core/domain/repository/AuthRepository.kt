package motiapps.melodify.core.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun getAuthUser(): FirebaseUser?
    suspend fun getAuthUserId(): String?
    suspend fun isUserAuthLoggedIn(): Boolean
}
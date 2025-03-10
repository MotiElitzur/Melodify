package motiapps.melodify.common.firebase.user.domain.repository

import com.google.firebase.auth.FirebaseUser
import melodify.core.domain.Resource

interface AuthRepository {

    suspend fun getAuthUser(): FirebaseUser?

    suspend fun getAuthUserId(): String?

    suspend fun isUserAuthLoggedIn(): Resource<Boolean>

    suspend fun logout(): Resource<Boolean>
}
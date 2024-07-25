package motiapps.melodify.core.domain.repository

import com.google.firebase.auth.FirebaseUser
import motiapps.melodify.core.domain.base.Resource

interface AuthRepository {

    suspend fun getAuthUser(): FirebaseUser?

    suspend fun getAuthUserId(): String?

    suspend fun isUserAuthLoggedIn(): Resource<Boolean>
}
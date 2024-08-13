package motiapps.melodify.features.login.domain.repository

import com.google.firebase.auth.FirebaseUser
import motiapps.melodify.core.domain.base.Resource

interface LoginRepository {

    suspend fun loginWithEmail(email: String, password: String): Resource<FirebaseUser>

    suspend fun loginAnonymously(): Resource<Unit>
}
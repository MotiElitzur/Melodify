package motiapps.melodify.features.register.domain.repository

import com.google.firebase.auth.FirebaseUser
import melodify.core.domain.Resource

interface RegisterRepository {

    suspend fun registerWithEmail(email: String, password: String): Resource<FirebaseUser>

    suspend fun createUserInDatabase(userId: String, firstName: String?, lastName: String?): Resource<Unit>
}
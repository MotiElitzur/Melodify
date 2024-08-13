package motiapps.melodify.features.register.domain.repository

import com.google.firebase.auth.FirebaseUser
import motiapps.melodify.core.domain.base.Resource

interface RegisterRepository {

    suspend fun registerWithEmail(email: String, password: String): Resource<FirebaseUser>

    suspend fun createUserInDatabase(userId: String, firstName: String?, lastName: String?): Resource<Unit>
}
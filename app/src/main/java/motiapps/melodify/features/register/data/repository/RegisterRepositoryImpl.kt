package motiapps.melodify.features.register.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.common.user.data.model.UserDto
import motiapps.melodify.features.register.domain.repository.RegisterRepository
import motiapps.melodify.core.presentation.base.error.BaseErrorType
import motiapps.melodify.core.presentation.base.error.RegisterErrorType
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : RegisterRepository {

    override suspend fun registerWithEmail(
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error(Exception("Register Authentication failed: user is null"))
            }
        } catch (exception: Exception) {
            val errorType = when (exception) {
                is FirebaseAuthWeakPasswordException -> RegisterErrorType.WeakPassword
                is FirebaseAuthUserCollisionException -> RegisterErrorType.UserAlreadyExists
                else -> BaseErrorType.Unknown
            }
            Resource.Error(exception, errorType)
        }
    }

    override suspend fun createUserInDatabase(
        userId: String,
        firstName: String?,
        lastName: String?
    ): Resource<Boolean> {

        try {
            val userDTO = UserDto(
                id = userId,
                firstName = firstName,
                lastName = lastName,
                email = firebaseAuth.currentUser?.email,
                creationTimestamp = Timestamp.now(),
                isAnonymous = false
            )
            firestore.collection("users").document(userId).set(userDTO).await()
            return Resource.Success(true)
        } catch (exception: Exception) {
            return Resource.Error(exception)
        }
    }
}
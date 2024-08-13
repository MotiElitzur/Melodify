package motiapps.melodify.features.register.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import motiapps.melodify.common.user.data.model.User
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.common.user.domain.usecases.UserUseCases
import motiapps.melodify.features.register.domain.repository.RegisterRepository
import motiapps.melodify.core.presentation.base.error.BaseErrorType
import motiapps.melodify.core.presentation.base.error.RegisterErrorType
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userUseCases: UserUseCases
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
    ): Resource<Unit> {

        try {
            val user = User(
                id = userId,
                firstName = firstName,
                lastName = lastName,
                email = firebaseAuth.currentUser?.email,
                creationTimestamp = Timestamp.now(),
                isAnonymous = false
            )

            return userUseCases.insertUser(user)
        } catch (exception: Exception) {
            return Resource.Error(exception)
        }
    }
}
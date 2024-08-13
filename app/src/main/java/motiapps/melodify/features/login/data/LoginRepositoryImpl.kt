package motiapps.melodify.features.login.data

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import motiapps.melodify.common.user.data.model.User
import motiapps.melodify.common.user.domain.usecases.UserUseCases
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.features.login.domain.repository.LoginRepository
import motiapps.melodify.core.presentation.base.error.BaseErrorType
import motiapps.melodify.core.presentation.base.error.LoginErrorType
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userUseCases: UserUseCases
) : LoginRepository {

    override suspend fun loginWithEmail(email: String, password: String): Resource<FirebaseUser> {
        return performLogin { firebaseAuth.signInWithEmailAndPassword(email, password).await() }
    }

    override suspend fun loginAnonymously(): Resource<Unit> {
        when (val resource = performLogin { firebaseAuth.signInAnonymously().await() }) {
            is Resource.Success -> {

                val authUser:FirebaseUser = resource.data
                val user = User(
                    id = authUser.uid,
                    creationTimestamp = Timestamp.now(),
                    isAnonymous = true
                )
                return userUseCases.insertUser(user)
            }
            is Resource.Error -> {
                return resource
            }
        }
    }

    private suspend fun performLogin(authAction: suspend () -> AuthResult): Resource<FirebaseUser> {
        return try {
            val authResult = authAction()
            val user = authResult.user
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error(Exception("Login Authentication failed: user is null"))
            }
        } catch (exception: Exception) {
            val errorType = when (exception) {
                is FirebaseAuthInvalidUserException -> LoginErrorType.UserNotFound
                is FirebaseNetworkException -> LoginErrorType.NoInternet
                is FirebaseAuthInvalidCredentialsException -> LoginErrorType.InvalidCredentials
                else -> BaseErrorType.Unknown
            }
            Resource.Error(exception, errorType)
        }
    }
}
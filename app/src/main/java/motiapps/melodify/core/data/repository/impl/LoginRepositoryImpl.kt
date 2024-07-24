package motiapps.melodify.core.data.repository.impl

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.repository.LoginRepository
import motiapps.melodify.core.presentation.base.error.BaseErrorType
import motiapps.melodify.core.presentation.base.error.LoginErrorType
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : LoginRepository {

    override suspend fun loginWithEmail(email: String, password: String): Resource<FirebaseUser> {
        return performLogin { firebaseAuth.signInWithEmailAndPassword(email, password).await() }
//        return try {
//            val authResult: AuthResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
//            val user = authResult.user
//            if (user != null) {
//                Resource.Success(user)
//            } else {
//                Resource.Error(Exception("Login Authentication failed: user is null"))
//            }
//        } catch (exception: Exception) {
//            Resource.Error(exception)
//        }
    }

    override suspend fun loginAnonymously(): Resource<FirebaseUser> {
        return performLogin { firebaseAuth.signInAnonymously().await() }
//        return try {
//            val authResult: AuthResult = firebaseAuth.signInAnonymously().await()
//            val user = authResult.user
//            if (user != null) {
//                Resource.Success(user)
//            } else {
//                Resource.Error(Exception("Login Authentication failed: user is null"))
//            }
//        } catch (exception: Exception) {
//            Resource.Error(exception)
//        }
    }

    override suspend fun logout(): Resource<Boolean> {
        return try {
            firebaseAuth.signOut()
            Resource.Success(true)
        } catch (exception: Exception) {
            Resource.Error(exception)
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
                is FirebaseAuthInvalidCredentialsException -> LoginErrorType.InvalidEmail // or InvalidPassword based on the exception details
                else -> BaseErrorType.Unknown
            }
            Resource.Error(exception, errorType)
        }
    }
}
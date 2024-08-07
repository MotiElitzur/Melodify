package motiapps.melodify.features.login.domain.usecase

import com.google.firebase.auth.FirebaseUser
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.features.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginAnonymousUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): SuspendUseCase<Nothing, Resource<FirebaseUser>>() {

    override suspend fun execute(params: Nothing?): Resource<FirebaseUser> {
        return loginRepository.loginAnonymously()
    }
}
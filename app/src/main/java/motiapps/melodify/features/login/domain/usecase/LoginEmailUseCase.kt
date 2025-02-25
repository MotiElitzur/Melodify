package motiapps.melodify.features.login.domain.usecase

import com.google.firebase.auth.FirebaseUser
import melodify.core.domain.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import motiapps.melodify.features.login.domain.repository.LoginRepository
import motiapps.melodify.features.login.domain.model.LoginCredentials
import javax.inject.Inject

class LoginEmailUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): SuspendUseCase<LoginCredentials, Resource<FirebaseUser>>() {

    override suspend fun execute(params: LoginCredentials?): Resource<FirebaseUser> {
        return loginRepository.loginWithEmail(params!!.email, params.password)
    }
}
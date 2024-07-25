package motiapps.melodify.features.login.domain.usecase

import com.google.firebase.auth.FirebaseUser
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.SuspendUseCase
import motiapps.melodify.core.domain.repository.LoginRepository
import motiapps.melodify.features.login.domain.LoginEmailUseCaseInput
import javax.inject.Inject

class LoginEmailUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): SuspendUseCase<LoginEmailUseCaseInput, Resource<FirebaseUser>>() {

    override suspend fun execute(params: LoginEmailUseCaseInput?): Resource<FirebaseUser> {
        return loginRepository.loginWithEmail(params!!.email, params.password)
    }
}
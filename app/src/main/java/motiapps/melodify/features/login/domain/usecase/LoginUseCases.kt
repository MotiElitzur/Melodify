package motiapps.melodify.features.login.domain.usecase

import javax.inject.Inject

data class LoginUseCases @Inject constructor(
    val loginAnonymousUseCase: LoginAnonymousUseCase,
    val loginWithEmailUseCase: LoginEmailUseCase,
)
package motiapps.melodify.features.register.domain.usecases

import javax.inject.Inject

data class RegisterUseCases @Inject constructor(
    val registerEmailPasswordUseCase: RegisterMailUseCase,
    val createUserInDbUseCase: CreateUserInDatabaseUseCase,
)
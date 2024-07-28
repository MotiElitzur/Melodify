package motiapps.melodify.features.register

import motiapps.melodify.features.register.domain.CreateUserInDatabaseUseCase
import motiapps.melodify.features.register.domain.RegisterMailUseCase
import javax.inject.Inject

data class RegisterUseCases @Inject constructor(
    val registerEmailPasswordUseCase: RegisterMailUseCase,
    val createUserInDbUseCase: CreateUserInDatabaseUseCase,
)
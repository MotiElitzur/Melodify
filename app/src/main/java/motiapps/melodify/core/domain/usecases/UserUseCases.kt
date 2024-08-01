package motiapps.melodify.core.domain.usecases

import motiapps.melodify.core.domain.usecases.user.DeleteUserUseCase
import motiapps.melodify.core.domain.usecases.user.FetchUserUseCase
import motiapps.melodify.core.domain.usecases.user.GetUserUseCase
import motiapps.melodify.core.domain.usecases.user.InsertUserUseCase
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val getUser: GetUserUseCase,
    val fetchUserUseCase: FetchUserUseCase,
    val insertUser: InsertUserUseCase,
    val deleteUserUseCase: DeleteUserUseCase
)
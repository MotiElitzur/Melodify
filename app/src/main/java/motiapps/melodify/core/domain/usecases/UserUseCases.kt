package motiapps.melodify.core.domain.usecases

import motiapps.melodify.core.domain.usecases.auth.UserAuthIdUseCase
import motiapps.melodify.core.domain.usecases.user.DeleteUserUseCase
import motiapps.melodify.core.domain.usecases.user.FetchUserUseCase
import motiapps.melodify.core.domain.usecases.user.GetUserUseCase
import motiapps.melodify.core.domain.usecases.user.InsertUserUseCase
import motiapps.melodify.core.domain.usecases.user.UpdateUserUseCase
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val getUserIdUseCase: UserAuthIdUseCase,
    val getUserUseCase: GetUserUseCase,
    val fetchUserUseCase: FetchUserUseCase,
    val updateUserUseCase: UpdateUserUseCase,
    val insertUser: InsertUserUseCase,
    val deleteUserUseCase: DeleteUserUseCase
)
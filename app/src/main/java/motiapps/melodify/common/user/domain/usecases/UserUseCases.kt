package motiapps.melodify.common.user.domain.usecases

import motiapps.melodify.common.user.domain.usecases.user.DeleteUserUseCase
import motiapps.melodify.common.user.domain.usecases.user.FetchUserUseCase
import motiapps.melodify.common.user.domain.usecases.user.GetUserUseCase
import motiapps.melodify.common.user.domain.usecases.user.InsertUserUseCase
import motiapps.melodify.common.user.domain.usecases.user.UpdateUserUseCase
import motiapps.melodify.common.firebase.user.domain.usecase.auth.UserAuthIdUseCase
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val getUserIdUseCase: UserAuthIdUseCase,
    val getUserUseCase: GetUserUseCase,
    val fetchUserUseCase: FetchUserUseCase,
    val updateUserUseCase: UpdateUserUseCase,
    val insertUser: InsertUserUseCase,
    val deleteUserUseCase: DeleteUserUseCase
)
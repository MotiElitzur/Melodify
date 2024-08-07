package motiapps.melodify.features.loading.data

import motiapps.melodify.core.common.user.data.model.User
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.common.user.domain.repository.UserRepository
import motiapps.melodify.features.loading.domain.LoadingRepository
import javax.inject.Inject

// Its currently only user loading but exists for supporting loading user data and more in the future
class LoadingRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository
) : LoadingRepository {

    override suspend fun loadUser(userId: String): Resource<User> {
        return userRepository.fetchUser(userId)
    }
}
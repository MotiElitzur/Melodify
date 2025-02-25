package motiapps.melodify.features.loading.domain

import motiapps.melodify.common.user.data.model.User
import melodify.core.domain.Resource

interface LoadingRepository {

    suspend fun loadUser(userId: String): Resource<User>
}
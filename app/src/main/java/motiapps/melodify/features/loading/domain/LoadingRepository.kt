package motiapps.melodify.features.loading.domain

import motiapps.melodify.core.data.model.User
import motiapps.melodify.core.domain.base.Resource

interface LoadingRepository {

    suspend fun loadUser(userId: String): Resource<User>
}
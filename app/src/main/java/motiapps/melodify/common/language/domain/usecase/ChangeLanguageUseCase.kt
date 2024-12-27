package motiapps.melodify.common.language.domain.usecase

import motiapps.melodify.common.language.domain.repository.LanguageRepository
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import javax.inject.Inject

class ChangeLanguageUseCase @Inject constructor(
    private val repository: LanguageRepository
) : SuspendUseCase<String, Resource<Unit>>() {

    override suspend fun execute(params: String?): Resource<Unit> {
        return if (params.isNullOrEmpty()) {
            Resource.Error(Exception("Language tag is null or empty"))
        } else {
            repository.setAppLanguage(params)
        }
    }
}

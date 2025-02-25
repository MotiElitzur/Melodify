package motiapps.melodify.common.language.domain.usecase

import motiapps.melodify.common.language.domain.repository.LanguageRepository
import melodify.core.domain.Resource
import motiapps.melodify.core.domain.base.usecase.SuspendUseCase
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val repository: LanguageRepository
) : SuspendUseCase<Unit, Resource<String>>() {

    override suspend fun execute(params: Unit?): Resource<String> {
        return repository.getAppLanguage()
    }
}
package motiapps.melodify.common.language.domain.usecase

import javax.inject.Inject

data class LanguageUseCases @Inject constructor(
    val getLanguagesUseCase: GetLanguageUseCase,
    val changeLanguageUseCase: ChangeLanguageUseCase
)
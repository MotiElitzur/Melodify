package motiapps.melodify.core.common.datastore.domain.usecase

import javax.inject.Inject

data class PreferencesUseCases @Inject constructor(
    val getPreferenceUseCase: GetPreferenceUseCase,
    val setPreferenceUseCase: SetPreferenceUseCase,
    val removePreferenceUseCase: RemovePreferenceUseCase,
    val observePreferenceUseCase: ObservePreferenceUseCase
)
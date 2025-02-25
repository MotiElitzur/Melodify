package motiapps.melodify.common.language.domain.repository

import melodify.core.domain.Resource

interface LanguageRepository {
    suspend fun setAppLanguage(languageTag: String): Resource<Unit>
    suspend fun getAppLanguage(): Resource<String>
}